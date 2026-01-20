package com.mycompany.app.auth.service;

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

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Property-based tests for password reset completion.
 * 
 * Feature: user-authentication
 * Property 6: Password Reset Completion
 * 
 * **Validates: Requirements 3.2, 3.3**
 * 
 * For any valid reset token and compliant new password, the password reset 
 * process should update the user's password hash and invalidate the token.
 */
public class PasswordResetCompletionPropertyTest {
    
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
     * Property 6: Password Reset Completion
     * 
     * For any valid reset token and compliant new password, the password reset 
     * process should update the user's password hash and invalidate the token.
     * 
     * **Validates: Requirements 3.2, 3.3**
     * 
     * This property test verifies:
     * - Password reset with valid token and new password succeeds
     * - User's password is updated (can login with new password)
     * - Token is invalidated after use (cannot be reused)
     * - Old password no longer works
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyPasswordResetCompletionUpdatesPasswordAndInvalidatesToken() {
        // Generate random credentials
        String username = generateRandomUsername();
        String oldPassword = generateRandomPassword();
        String newPassword = generateDifferentPassword(oldPassword);
        
        // Create and save user with old password
        String hashedOldPassword = passwordHasher.hashPassword(oldPassword);
        User user = new User(username, hashedOldPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Initiate password reset
        PasswordResetResult resetResult = authService.initiatePasswordReset(username);
        assertTrue(resetResult.isSuccessful(), 
            "Password reset initiation should succeed for user: " + username);
        
        String token = resetResult.getToken();
        assertNotNull(token, "Token should be generated");
        
        // Property 1: Complete password reset with valid token and new password
        boolean resetSuccess = authService.completePasswordReset(token, newPassword);
        assertTrue(resetSuccess, 
            "Password reset completion should succeed with valid token for user: " + username);
        
        // Property 2: Token should be invalidated after use (Requirement 3.3)
        assertFalse(tokenRepository.findByToken(token).isPresent(), 
            "Token should be invalidated after successful password reset for user: " + username);
        
        // Property 3: User should be able to login with new password (Requirement 3.2)
        AuthenticationResult loginWithNewPassword = authService.login(username, newPassword);
        assertTrue(loginWithNewPassword.isSuccessful(), 
            "User should be able to login with new password for user: " + username);
        assertNotNull(loginWithNewPassword.getSessionId(), 
            "Login with new password should create session for user: " + username);
        
        // Property 4: User should NOT be able to login with old password
        AuthenticationResult loginWithOldPassword = authService.login(username, oldPassword);
        assertFalse(loginWithOldPassword.isSuccessful(), 
            "User should NOT be able to login with old password for user: " + username);
        assertNull(loginWithOldPassword.getSessionId(), 
            "Login with old password should not create session for user: " + username);
    }
    
    /**
     * Property test: Token cannot be reused after successful password reset
     * 
     * **Validates: Requirements 3.3**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyTokenCannotBeReusedAfterSuccessfulReset() {
        // Generate random credentials
        String username = generateRandomUsername();
        String oldPassword = generateRandomPassword();
        String newPassword1 = generateDifferentPassword(oldPassword);
        String newPassword2 = generateDifferentPassword(newPassword1);
        
        // Create and save user
        String hashedPassword = passwordHasher.hashPassword(oldPassword);
        User user = new User(username, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Initiate password reset and get token
        PasswordResetResult resetResult = authService.initiatePasswordReset(username);
        String token = resetResult.getToken();
        
        // Complete password reset with first new password
        boolean firstReset = authService.completePasswordReset(token, newPassword1);
        assertTrue(firstReset, "First password reset should succeed");
        
        // Property: Attempt to reuse the same token should fail
        boolean secondReset = authService.completePasswordReset(token, newPassword2);
        assertFalse(secondReset, 
            "Token reuse should fail after successful password reset for user: " + username);
        
        // Verify password is still the first new password (not the second)
        AuthenticationResult loginWithNewPassword1 = authService.login(username, newPassword1);
        assertTrue(loginWithNewPassword1.isSuccessful(), 
            "User should be able to login with first new password");
        
        AuthenticationResult loginWithNewPassword2 = authService.login(username, newPassword2);
        assertFalse(loginWithNewPassword2.isSuccessful(), 
            "User should NOT be able to login with second new password (token was reused)");
    }
    
    /**
     * Property test: Password reset with invalid token fails
     * 
     * **Validates: Requirements 3.2**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyPasswordResetWithInvalidTokenFails() {
        // Generate random credentials
        String username = generateRandomUsername();
        String password = generateRandomPassword();
        String newPassword = generateDifferentPassword(password);
        
        // Create and save user
        String hashedPassword = passwordHasher.hashPassword(password);
        User user = new User(username, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Generate a fake token that doesn't exist
        String fakeToken = generateRandomToken();
        
        // Property: Password reset with invalid token should fail
        boolean resetSuccess = authService.completePasswordReset(fakeToken, newPassword);
        assertFalse(resetSuccess, 
            "Password reset with invalid token should fail for user: " + username);
        
        // Verify password is unchanged (can still login with old password)
        AuthenticationResult loginWithOldPassword = authService.login(username, password);
        assertTrue(loginWithOldPassword.isSuccessful(), 
            "User should still be able to login with old password after failed reset");
        
        // Verify cannot login with new password
        AuthenticationResult loginWithNewPassword = authService.login(username, newPassword);
        assertFalse(loginWithNewPassword.isSuccessful(), 
            "User should NOT be able to login with new password after failed reset");
    }
    
    /**
     * Property test: Password reset updates password hash correctly
     * 
     * **Validates: Requirements 3.2**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyPasswordResetUpdatesPasswordHashCorrectly() {
        // Generate random credentials
        String username = generateRandomUsername();
        String oldPassword = generateRandomPassword();
        String newPassword = generateDifferentPassword(oldPassword);
        
        // Create and save user
        String hashedOldPassword = passwordHasher.hashPassword(oldPassword);
        User user = new User(username, hashedOldPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Get the old password hash
        Optional<User> userBeforeReset = userRepository.findByUsername(username);
        assertTrue(userBeforeReset.isPresent(), "User should exist before reset");
        String oldHash = userBeforeReset.get().getHashedPassword();
        
        // Initiate and complete password reset
        PasswordResetResult resetResult = authService.initiatePasswordReset(username);
        String token = resetResult.getToken();
        boolean resetSuccess = authService.completePasswordReset(token, newPassword);
        assertTrue(resetSuccess, "Password reset should succeed");
        
        // Get the new password hash
        Optional<User> userAfterReset = userRepository.findByUsername(username);
        assertTrue(userAfterReset.isPresent(), "User should exist after reset");
        String newHash = userAfterReset.get().getHashedPassword();
        
        // Property: Password hash should be different after reset
        assertNotEquals(oldHash, newHash, 
            "Password hash should be updated after password reset for user: " + username);
        
        // Property: New hash should verify against new password
        assertTrue(passwordHasher.verifyPassword(newPassword, newHash), 
            "New password hash should verify against new password for user: " + username);
        
        // Property: New hash should NOT verify against old password
        assertFalse(passwordHasher.verifyPassword(oldPassword, newHash), 
            "New password hash should NOT verify against old password for user: " + username);
    }
    
    /**
     * Property test: Multiple password resets work correctly
     * 
     * **Validates: Requirements 3.2, 3.3**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyMultiplePasswordResetsWorkCorrectly() {
        // Generate random credentials
        String username = generateRandomUsername();
        String password1 = generateRandomPassword();
        String password2 = generateDifferentPassword(password1);
        String password3 = generateDifferentPassword(password2);
        
        // Create and save user with password1
        String hashedPassword1 = passwordHasher.hashPassword(password1);
        User user = new User(username, hashedPassword1, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // First password reset: password1 -> password2
        PasswordResetResult resetResult1 = authService.initiatePasswordReset(username);
        String token1 = resetResult1.getToken();
        boolean reset1Success = authService.completePasswordReset(token1, password2);
        assertTrue(reset1Success, "First password reset should succeed");
        
        // Verify can login with password2
        AuthenticationResult login2 = authService.login(username, password2);
        assertTrue(login2.isSuccessful(), "Should be able to login with password2");
        
        // Second password reset: password2 -> password3
        PasswordResetResult resetResult2 = authService.initiatePasswordReset(username);
        String token2 = resetResult2.getToken();
        boolean reset2Success = authService.completePasswordReset(token2, password3);
        assertTrue(reset2Success, "Second password reset should succeed");
        
        // Property: Can login with password3
        AuthenticationResult login3 = authService.login(username, password3);
        assertTrue(login3.isSuccessful(), 
            "Should be able to login with password3 after second reset");
        
        // Property: Cannot login with password1 or password2
        AuthenticationResult login1 = authService.login(username, password1);
        assertFalse(login1.isSuccessful(), 
            "Should NOT be able to login with password1 after resets");
        
        AuthenticationResult login2Again = authService.login(username, password2);
        assertFalse(login2Again.isSuccessful(), 
            "Should NOT be able to login with password2 after second reset");
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
    
    private String generateRandomToken() {
        // Generate a fake token that looks like a real one but doesn't exist
        StringBuilder token = new StringBuilder();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        
        for (int i = 0; i < 64; i++) {
            token.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        return token.toString();
    }
}
