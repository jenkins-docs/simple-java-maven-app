package com.mycompany.app.auth.service;

import com.mycompany.app.auth.model.PasswordResetToken;
import com.mycompany.app.auth.model.User;
import com.mycompany.app.auth.repository.InMemoryPasswordResetTokenRepository;
import com.mycompany.app.auth.repository.InMemorySessionRepository;
import com.mycompany.app.auth.repository.InMemoryUserRepository;
import com.mycompany.app.auth.repository.PasswordResetTokenRepository;
import com.mycompany.app.auth.repository.SessionRepository;
import com.mycompany.app.auth.repository.UserRepository;
import com.mycompany.app.auth.result.AuthenticationResult;
import com.mycompany.app.auth.result.PasswordResetResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Property-based tests for token expiration handling.
 * 
 * Feature: user-authentication
 * Property 7: Token Expiration Handling
 * 
 * **Validates: Requirements 3.4**
 * 
 * For any expired password reset token, attempts to use it should be rejected 
 * regardless of other parameters.
 */
public class TokenExpirationPropertyTest {
    
    private static final int PROPERTY_TEST_ITERATIONS = 20;
    private static final Random random = new Random();
    
    private AuthenticationService authService;
    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private PasswordResetTokenRepository tokenRepository;
    private PasswordHasher passwordHasher;
    
    @BeforeEach
    public void setUp() {
        userRepository = new InMemoryUserRepository();
        sessionRepository = new InMemorySessionRepository();
        tokenRepository = new InMemoryPasswordResetTokenRepository();
        // Use minimum work factor (4) for faster tests
        passwordHasher = new PasswordHasher(4);
        SecureIdentifierGenerator identifierGenerator = new SecureIdentifierGenerator();
        
        authService = new AuthenticationServiceImpl(
            userRepository,
            sessionRepository,
            tokenRepository,
            passwordHasher,
            identifierGenerator
        );
    }
    
    /**
     * Property 7: Token Expiration Handling
     * 
     * For any expired password reset token, attempts to use it should be rejected 
     * regardless of other parameters.
     * 
     * **Validates: Requirements 3.4**
     * 
     * This property test verifies:
     * - Expired tokens are rejected during password reset
     * - Password is not changed when using expired token
     * - Expired tokens are cleaned up from the repository
     */
    @Test
    public void propertyExpiredTokenIsRejected() {
        // Generate random credentials
        String username = generateRandomUsername();
        String oldPassword = generateRandomPassword();
        String newPassword = generateDifferentPassword(oldPassword);
        
        // Create and save user
        String hashedPassword = passwordHasher.hashPassword(oldPassword);
        User user = new User(username, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Create an expired token manually
        String expiredToken = "expired_token_" + random.nextInt(100000);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredTime = now.minusHours(2); // Expired 2 hours ago
        PasswordResetToken resetToken = new PasswordResetToken(
            expiredToken, 
            username, 
            expiredTime.minusHours(3), // Created 3 hours ago
            expiredTime // Expired 2 hours ago
        );
        tokenRepository.save(resetToken);
        
        // Property 1: Attempt to use expired token should fail
        boolean resetSuccess = authService.completePasswordReset(expiredToken, newPassword);
        assertFalse(resetSuccess, 
            "Password reset with expired token should fail for user: " + username);
        
        // Property 2: Password should remain unchanged (can still login with old password)
        AuthenticationResult loginWithOldPassword = authService.login(username, oldPassword);
        assertTrue(loginWithOldPassword.isSuccessful(), 
            "User should still be able to login with old password after expired token attempt");
        
        // Property 3: Cannot login with new password
        AuthenticationResult loginWithNewPassword = authService.login(username, newPassword);
        assertFalse(loginWithNewPassword.isSuccessful(), 
            "User should NOT be able to login with new password after expired token attempt");
    }
    
    /**
     * Property test: Expired token is removed from repository after use attempt
     * 
     * **Validates: Requirements 3.4**
     */
    @Test
    public void propertyExpiredTokenIsRemovedAfterUseAttempt() {
        // Generate random credentials
        String username = generateRandomUsername();
        String password = generateRandomPassword();
        String newPassword = generateDifferentPassword(password);
        
        // Create and save user
        String hashedPassword = passwordHasher.hashPassword(password);
        User user = new User(username, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Create an expired token
        String expiredToken = "expired_token_" + random.nextInt(100000);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredTime = now.minusHours(2);
        PasswordResetToken resetToken = new PasswordResetToken(
            expiredToken, 
            username, 
            expiredTime.minusHours(3),
            expiredTime
        );
        tokenRepository.save(resetToken);
        
        // Verify token exists before use attempt
        assertTrue(tokenRepository.findByToken(expiredToken).isPresent(), 
            "Expired token should exist in repository before use attempt");
        
        // Attempt to use expired token
        boolean resetSuccess = authService.completePasswordReset(expiredToken, newPassword);
        assertFalse(resetSuccess, "Password reset with expired token should fail");
        
        // Property: Expired token should be removed from repository after use attempt
        assertFalse(tokenRepository.findByToken(expiredToken).isPresent(), 
            "Expired token should be removed from repository after use attempt");
    }
    
    /**
     * Property test: Token that expires exactly at current time is treated as expired
     * 
     * **Validates: Requirements 3.4**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyTokenExpiringAtCurrentTimeIsExpired() {
        // Generate random credentials
        String username = generateRandomUsername();
        String password = generateRandomPassword();
        String newPassword = generateDifferentPassword(password);
        
        // Create and save user
        String hashedPassword = passwordHasher.hashPassword(password);
        User user = new User(username, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Create a token that expires in the past (even by 1 second)
        String token = "token_" + random.nextInt(100000);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredTime = now.minusSeconds(1); // Expired 1 second ago
        PasswordResetToken resetToken = new PasswordResetToken(
            token, 
            username, 
            expiredTime.minusHours(1),
            expiredTime
        );
        tokenRepository.save(resetToken);
        
        // Property: Token should be rejected as expired
        boolean resetSuccess = authService.completePasswordReset(token, newPassword);
        assertFalse(resetSuccess, 
            "Token that expired even 1 second ago should be rejected for user: " + username);
        
        // Verify password is unchanged
        AuthenticationResult loginWithOldPassword = authService.login(username, password);
        assertTrue(loginWithOldPassword.isSuccessful(), 
            "User should still be able to login with old password");
    }
    
    /**
     * Property test: Valid (non-expired) token works correctly
     * 
     * **Validates: Requirements 3.4**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyNonExpiredTokenWorks() {
        // Generate random credentials
        String username = generateRandomUsername();
        String password = generateRandomPassword();
        String newPassword = generateDifferentPassword(password);
        
        // Create and save user
        String hashedPassword = passwordHasher.hashPassword(password);
        User user = new User(username, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Create a valid (non-expired) token
        String token = "token_" + random.nextInt(100000);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime futureExpiration = now.plusHours(1); // Expires in 1 hour
        PasswordResetToken resetToken = new PasswordResetToken(
            token, 
            username, 
            now,
            futureExpiration
        );
        tokenRepository.save(resetToken);
        
        // Property: Non-expired token should work
        boolean resetSuccess = authService.completePasswordReset(token, newPassword);
        assertTrue(resetSuccess, 
            "Password reset with non-expired token should succeed for user: " + username);
        
        // Verify password is changed
        AuthenticationResult loginWithNewPassword = authService.login(username, newPassword);
        assertTrue(loginWithNewPassword.isSuccessful(), 
            "User should be able to login with new password after successful reset");
    }
    
    /**
     * Property test: Token expiration is checked before password validation
     * 
     * **Validates: Requirements 3.4**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyTokenExpirationCheckedBeforePasswordValidation() {
        // Generate random credentials
        String username = generateRandomUsername();
        String password = generateRandomPassword();
        String invalidNewPassword = "short"; // Too short, invalid password
        
        // Create and save user
        String hashedPassword = passwordHasher.hashPassword(password);
        User user = new User(username, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Create an expired token
        String expiredToken = "expired_token_" + random.nextInt(100000);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredTime = now.minusHours(2);
        PasswordResetToken resetToken = new PasswordResetToken(
            expiredToken, 
            username, 
            expiredTime.minusHours(3),
            expiredTime
        );
        tokenRepository.save(resetToken);
        
        // Property: Even with invalid password, expired token should be rejected
        // (expiration is checked first)
        boolean resetSuccess = authService.completePasswordReset(expiredToken, invalidNewPassword);
        assertFalse(resetSuccess, 
            "Password reset with expired token should fail even with invalid password");
        
        // Verify password is unchanged
        AuthenticationResult loginWithOldPassword = authService.login(username, password);
        assertTrue(loginWithOldPassword.isSuccessful(), 
            "User should still be able to login with old password");
    }
    
    /**
     * Property test: Multiple expired tokens for same user are all rejected
     * 
     * **Validates: Requirements 3.4**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyMultipleExpiredTokensAreRejected() {
        // Generate random credentials
        String username = generateRandomUsername();
        String password = generateRandomPassword();
        
        // Create and save user
        String hashedPassword = passwordHasher.hashPassword(password);
        User user = new User(username, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Create multiple expired tokens (simulating multiple reset requests over time)
        LocalDateTime now = LocalDateTime.now();
        int numExpiredTokens = 3 + random.nextInt(3); // 3-5 expired tokens
        
        for (int i = 0; i < numExpiredTokens; i++) {
            String token = "expired_token_" + i + "_" + random.nextInt(100000);
            LocalDateTime expiredTime = now.minusHours(i + 1);
            PasswordResetToken resetToken = new PasswordResetToken(
                token, 
                username, 
                expiredTime.minusHours(1),
                expiredTime
            );
            tokenRepository.save(resetToken);
            
            // Property: Each expired token should be rejected
            String newPassword = generateDifferentPassword(password);
            boolean resetSuccess = authService.completePasswordReset(token, newPassword);
            assertFalse(resetSuccess, 
                "Expired token " + i + " should be rejected for user: " + username);
        }
        
        // Verify password is unchanged
        AuthenticationResult loginWithOldPassword = authService.login(username, password);
        assertTrue(loginWithOldPassword.isSuccessful(), 
            "User should still be able to login with old password after all expired token attempts");
    }
    
    // ========== Test Data Generators ==========
    
    private String generateRandomUsername() {
        String[] prefixes = {"user", "admin", "test", "demo", "guest"};
        String prefix = prefixes[random.nextInt(prefixes.length)];
        int suffix = random.nextInt(100000);
        return prefix + suffix;
    }
    
    private String generateRandomPassword() {
        int length = 8 + random.nextInt(25); // Length between 8 and 32
        StringBuilder password = new StringBuilder();
        
        // Guarantee at least one of each required character type
        password.append((char) ('A' + random.nextInt(26))); // Uppercase
        password.append((char) ('a' + random.nextInt(26))); // Lowercase
        password.append((char) ('0' + random.nextInt(10))); // Digit
        
        // Fill the rest with random characters
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+-=[]{}|;:,.<>?";
        for (int i = 3; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        // Shuffle to randomize positions
        return shuffleString(password.toString());
    }
    
    private String shuffleString(String input) {
        char[] chars = input.toCharArray();
        for (int i = chars.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }
        return new String(chars);
    }
    
    private String generateDifferentPassword(String originalPassword) {
        String differentPassword;
        do {
            differentPassword = generateRandomPassword();
        } while (differentPassword.equals(originalPassword));
        
        return differentPassword;
    }
}
