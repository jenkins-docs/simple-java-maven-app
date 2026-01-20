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
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Property-based tests for password validation.
 * 
 * Feature: user-authentication
 * Property 8: Password Validation
 * 
 * **Validates: Requirements 3.5**
 * 
 * For any new password that doesn't meet security requirements, the system 
 * should reject it with appropriate validation errors.
 */
public class PasswordValidationPropertyTest {
    
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
     * Property 8: Password Validation - Minimum Length
     * 
     * For any password shorter than minimum length (8 characters), the system 
     * should reject it during password reset.
     * 
     * **Validates: Requirements 3.5**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyPasswordTooShortIsRejected() {
        // Generate random credentials
        String username = generateRandomUsername();
        String oldPassword = generateRandomPassword();
        
        // Generate a password that's too short (less than 8 characters)
        int shortLength = 1 + random.nextInt(7); // Length between 1 and 7
        String shortPassword = generatePasswordOfLength(shortLength);
        
        // Create and save user
        String hashedPassword = passwordHasher.hashPassword(oldPassword);
        User user = new User(username, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Initiate password reset
        PasswordResetResult resetResult = authService.initiatePasswordReset(username);
        String token = resetResult.getToken();
        
        // Property: Password reset with too-short password should fail
        boolean resetSuccess = authService.completePasswordReset(token, shortPassword);
        assertFalse(resetSuccess, 
            "Password reset with password of length " + shortLength + " should fail for user: " + username);
        
        // Verify password is unchanged (can still login with old password)
        AuthenticationResult loginWithOldPassword = authService.login(username, oldPassword);
        assertTrue(loginWithOldPassword.isSuccessful(), 
            "User should still be able to login with old password after rejected short password");
        
        // Verify cannot login with short password
        AuthenticationResult loginWithShortPassword = authService.login(username, shortPassword);
        assertFalse(loginWithShortPassword.isSuccessful(), 
            "User should NOT be able to login with rejected short password");
    }
    
    /**
     * Property test: Password with exactly minimum length (8 characters) is accepted
     * 
     * **Validates: Requirements 3.5**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyPasswordWithMinimumLengthIsAccepted() {
        // Generate random credentials
        String username = generateRandomUsername();
        String oldPassword = generateRandomPassword();
        String newPassword = generatePasswordOfLength(8); // Exactly 8 characters
        
        // Create and save user
        String hashedPassword = passwordHasher.hashPassword(oldPassword);
        User user = new User(username, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Initiate password reset
        PasswordResetResult resetResult = authService.initiatePasswordReset(username);
        String token = resetResult.getToken();
        
        // Property: Password reset with 8-character password should succeed
        boolean resetSuccess = authService.completePasswordReset(token, newPassword);
        assertTrue(resetSuccess, 
            "Password reset with 8-character password should succeed for user: " + username);
        
        // Verify can login with new password
        AuthenticationResult loginWithNewPassword = authService.login(username, newPassword);
        assertTrue(loginWithNewPassword.isSuccessful(), 
            "User should be able to login with 8-character password");
    }
    
    /**
     * Property test: Password with maximum length (128 characters) is accepted
     * 
     * **Validates: Requirements 3.5**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyPasswordWithMaximumLengthIsAccepted() {
        // Generate random credentials
        String username = generateRandomUsername();
        String oldPassword = generateRandomPassword();
        String newPassword = generatePasswordOfLength(128); // Exactly 128 characters
        
        // Create and save user
        String hashedPassword = passwordHasher.hashPassword(oldPassword);
        User user = new User(username, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Initiate password reset
        PasswordResetResult resetResult = authService.initiatePasswordReset(username);
        String token = resetResult.getToken();
        
        // Property: Password reset with 128-character password should succeed
        boolean resetSuccess = authService.completePasswordReset(token, newPassword);
        assertTrue(resetSuccess, 
            "Password reset with 128-character password should succeed for user: " + username);
        
        // Verify can login with new password
        AuthenticationResult loginWithNewPassword = authService.login(username, newPassword);
        assertTrue(loginWithNewPassword.isSuccessful(), 
            "User should be able to login with 128-character password");
    }
    
    /**
     * Property test: Password longer than maximum length (>128 characters) is rejected
     * 
     * **Validates: Requirements 3.5**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyPasswordTooLongIsRejected() {
        // Generate random credentials
        String username = generateRandomUsername();
        String oldPassword = generateRandomPassword();
        
        // Generate a password that's too long (more than 128 characters)
        int longLength = 129 + random.nextInt(100); // Length between 129 and 228
        String longPassword = generatePasswordOfLength(longLength);
        
        // Create and save user
        String hashedPassword = passwordHasher.hashPassword(oldPassword);
        User user = new User(username, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Initiate password reset
        PasswordResetResult resetResult = authService.initiatePasswordReset(username);
        String token = resetResult.getToken();
        
        // Property: Password reset with too-long password should fail
        boolean resetSuccess = authService.completePasswordReset(token, longPassword);
        assertFalse(resetSuccess, 
            "Password reset with password of length " + longLength + " should fail for user: " + username);
        
        // Verify password is unchanged
        AuthenticationResult loginWithOldPassword = authService.login(username, oldPassword);
        assertTrue(loginWithOldPassword.isSuccessful(), 
            "User should still be able to login with old password after rejected long password");
    }
    
    /**
     * Property test: Empty password is rejected
     * 
     * **Validates: Requirements 3.5**
     */
    @Test
    public void propertyEmptyPasswordIsRejected() {
        // Generate random credentials
        String username = generateRandomUsername();
        String oldPassword = generateRandomPassword();
        
        // Create and save user
        String hashedPassword = passwordHasher.hashPassword(oldPassword);
        User user = new User(username, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Initiate password reset
        PasswordResetResult resetResult = authService.initiatePasswordReset(username);
        String token = resetResult.getToken();
        
        // Property: Empty password should throw exception
        assertThrows(IllegalArgumentException.class, () -> {
            authService.completePasswordReset(token, "");
        }, "Empty password should throw IllegalArgumentException");
        
        // Verify password is unchanged
        AuthenticationResult loginWithOldPassword = authService.login(username, oldPassword);
        assertTrue(loginWithOldPassword.isSuccessful(), 
            "User should still be able to login with old password after empty password attempt");
    }
    
    /**
     * Property test: Null password is rejected
     * 
     * **Validates: Requirements 3.5**
     */
    @Test
    public void propertyNullPasswordIsRejected() {
        // Generate random credentials
        String username = generateRandomUsername();
        String oldPassword = generateRandomPassword();
        
        // Create and save user
        String hashedPassword = passwordHasher.hashPassword(oldPassword);
        User user = new User(username, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Initiate password reset
        PasswordResetResult resetResult = authService.initiatePasswordReset(username);
        String token = resetResult.getToken();
        
        // Property: Null password should throw exception
        assertThrows(IllegalArgumentException.class, () -> {
            authService.completePasswordReset(token, null);
        }, "Null password should throw IllegalArgumentException");
        
        // Verify password is unchanged
        AuthenticationResult loginWithOldPassword = authService.login(username, oldPassword);
        assertTrue(loginWithOldPassword.isSuccessful(), 
            "User should still be able to login with old password after null password attempt");
    }
    
    /**
     * Property test: Whitespace-only password is rejected
     * 
     * **Validates: Requirements 3.5**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyWhitespaceOnlyPasswordIsRejected() {
        // Generate random credentials
        String username = generateRandomUsername();
        String oldPassword = generateRandomPassword();
        
        // Generate a whitespace-only password of various lengths
        int length = 8 + random.nextInt(25); // Length between 8 and 32
        StringBuilder whitespacePassword = new StringBuilder();
        for (int i = 0; i < length; i++) {
            whitespacePassword.append(' ');
        }
        
        // Create and save user
        String hashedPassword = passwordHasher.hashPassword(oldPassword);
        User user = new User(username, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Initiate password reset
        PasswordResetResult resetResult = authService.initiatePasswordReset(username);
        String token = resetResult.getToken();
        
        // Property: Whitespace-only password should be rejected
        boolean resetSuccess = authService.completePasswordReset(token, whitespacePassword.toString());
        assertFalse(resetSuccess, 
            "Password reset with whitespace-only password should fail for user: " + username);
        
        // Verify password is unchanged
        AuthenticationResult loginWithOldPassword = authService.login(username, oldPassword);
        assertTrue(loginWithOldPassword.isSuccessful(), 
            "User should still be able to login with old password after whitespace-only password attempt");
    }
    
    /**
     * Property test: Valid passwords of various lengths are accepted
     * 
     * **Validates: Requirements 3.5**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyValidPasswordsOfVariousLengthsAreAccepted() {
        // Generate random credentials
        String username = generateRandomUsername();
        String oldPassword = generateRandomPassword();
        
        // Generate a valid password of random length between 8 and 128
        int validLength = 8 + random.nextInt(121); // Length between 8 and 128
        String newPassword = generatePasswordOfLength(validLength);
        
        // Create and save user
        String hashedPassword = passwordHasher.hashPassword(oldPassword);
        User user = new User(username, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Initiate password reset
        PasswordResetResult resetResult = authService.initiatePasswordReset(username);
        String token = resetResult.getToken();
        
        // Property: Valid password should be accepted
        boolean resetSuccess = authService.completePasswordReset(token, newPassword);
        assertTrue(resetSuccess, 
            "Password reset with valid password of length " + validLength + " should succeed for user: " + username);
        
        // Verify can login with new password
        AuthenticationResult loginWithNewPassword = authService.login(username, newPassword);
        assertTrue(loginWithNewPassword.isSuccessful(), 
            "User should be able to login with new password of length " + validLength);
    }
    
    /**
     * Property test: Password validation is consistent across multiple attempts
     * 
     * **Validates: Requirements 3.5**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyPasswordValidationIsConsistent() {
        // Generate random credentials
        String username = generateRandomUsername();
        String oldPassword = generateRandomPassword();
        
        // Generate an invalid password (too short)
        String invalidPassword = generatePasswordOfLength(5);
        
        // Create and save user
        String hashedPassword = passwordHasher.hashPassword(oldPassword);
        User user = new User(username, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Try multiple password reset attempts with the same invalid password
        for (int i = 0; i < 3; i++) {
            // Initiate new password reset for each attempt
            PasswordResetResult resetResult = authService.initiatePasswordReset(username);
            String token = resetResult.getToken();
            
            // Property: Invalid password should be consistently rejected
            boolean resetSuccess = authService.completePasswordReset(token, invalidPassword);
            assertFalse(resetSuccess, 
                "Attempt " + i + ": Invalid password should be consistently rejected for user: " + username);
        }
        
        // Verify password is still unchanged
        AuthenticationResult loginWithOldPassword = authService.login(username, oldPassword);
        assertTrue(loginWithOldPassword.isSuccessful(), 
            "User should still be able to login with old password after multiple invalid password attempts");
    }
    
    /**
     * Property test: Password with special characters is accepted
     * 
     * **Validates: Requirements 3.5**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyPasswordWithSpecialCharactersIsAccepted() {
        // Generate random credentials
        String username = generateRandomUsername();
        String oldPassword = generateRandomPassword();
        
        // Generate a password with special characters
        String newPassword = generatePasswordWithSpecialChars();
        
        // Create and save user
        String hashedPassword = passwordHasher.hashPassword(oldPassword);
        User user = new User(username, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Initiate password reset
        PasswordResetResult resetResult = authService.initiatePasswordReset(username);
        String token = resetResult.getToken();
        
        // Property: Password with special characters should be accepted
        boolean resetSuccess = authService.completePasswordReset(token, newPassword);
        assertTrue(resetSuccess, 
            "Password reset with special characters should succeed for user: " + username);
        
        // Verify can login with new password
        AuthenticationResult loginWithNewPassword = authService.login(username, newPassword);
        assertTrue(loginWithNewPassword.isSuccessful(), 
            "User should be able to login with password containing special characters");
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
        return generatePasswordOfLength(length);
    }
    
    private String generatePasswordOfLength(int length) {
        StringBuilder password = new StringBuilder();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        return password.toString();
    }
    
    private String generatePasswordWithSpecialChars() {
        int length = 10 + random.nextInt(11); // Length between 10 and 20
        StringBuilder password = new StringBuilder();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String specialChars = "!@#$%^&*()_+-=[]{}|;:,.<>?";
        
        // Add some regular characters
        for (int i = 0; i < length - 3; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        // Add some special characters
        for (int i = 0; i < 3; i++) {
            password.append(specialChars.charAt(random.nextInt(specialChars.length())));
        }
        
        return password.toString();
    }
}
