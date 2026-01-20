package com.mycompany.app.auth.service;

import com.mycompany.app.auth.model.User;
import com.mycompany.app.auth.repository.InMemoryPasswordResetTokenRepository;
import com.mycompany.app.auth.repository.InMemorySessionRepository;
import com.mycompany.app.auth.repository.InMemoryUserRepository;
import com.mycompany.app.auth.repository.PasswordResetTokenRepository;
import com.mycompany.app.auth.repository.SessionRepository;
import com.mycompany.app.auth.repository.UserRepository;
import com.mycompany.app.auth.result.AuthenticationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.time.LocalDateTime;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Property-based tests for information disclosure prevention.
 * 
 * Feature: user-authentication
 * Property 11: Information Disclosure Prevention
 * 
 * **Validates: Requirements 1.3, 3.6, 5.5**
 * 
 * For any authentication operation (login, password reset), error messages should 
 * not reveal whether specific usernames exist in the system.
 */
public class InformationDisclosurePropertyTest {
    
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
     * Property 11: Information Disclosure Prevention - Login Error Messages
     * 
     * For any failed login attempt, the error message should not reveal whether 
     * the username exists or the password is wrong.
     * 
     * **Validates: Requirements 1.3, 5.5**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyLoginErrorMessagesDoNotRevealUserExistence() {
        // Generate random credentials
        String existingUsername = generateRandomUsername();
        String nonExistentUsername = generateRandomUsername();
        String correctPassword = generateRandomPassword();
        String wrongPassword = generateDifferentPassword(correctPassword);
        
        // Create and save user with existing username
        String hashedPassword = passwordHasher.hashPassword(correctPassword);
        User user = new User(existingUsername, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Scenario 1: Existing user with wrong password
        AuthenticationResult result1 = authService.login(existingUsername, wrongPassword);
        
        // Scenario 2: Non-existent user with any password
        AuthenticationResult result2 = authService.login(nonExistentUsername, wrongPassword);
        
        // Property 1: Both scenarios should fail
        assertFalse(result1.isSuccessful(), 
            "Login with wrong password should fail for existing user: " + existingUsername);
        assertFalse(result2.isSuccessful(), 
            "Login with non-existent user should fail: " + nonExistentUsername);
        
        // Property 2: Error messages should be identical (generic)
        assertEquals(result1.getErrorMessage(), result2.getErrorMessage(), 
            "Error messages should be identical for wrong password vs non-existent user");
        
        // Property 3: Error message should not contain username
        assertFalse(result1.getErrorMessage().contains(existingUsername), 
            "Error message should not contain existing username: " + existingUsername);
        assertFalse(result2.getErrorMessage().contains(nonExistentUsername), 
            "Error message should not contain non-existent username: " + nonExistentUsername);
        
        // Property 4: Error message should not reveal specific failure reason
        String errorMsg = result1.getErrorMessage().toLowerCase();
        assertFalse(errorMsg.contains("user not found") || errorMsg.contains("user does not exist"), 
            "Error message should not reveal user existence");
        assertFalse(errorMsg.contains("wrong password") || errorMsg.contains("incorrect password"), 
            "Error message should not reveal password is wrong");
        assertFalse(errorMsg.contains("username") && errorMsg.contains("not found"), 
            "Error message should not reveal username lookup failure");
    }
    
    /**
     * Property test: Error messages are consistent across multiple failed attempts
     * 
     * **Validates: Requirements 1.3, 5.5**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyErrorMessagesAreConsistent() {
        // Generate random credentials
        String username = generateRandomUsername();
        String password = generateRandomPassword();
        
        // Create and save user
        String hashedPassword = passwordHasher.hashPassword(password);
        User user = new User(username, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Attempt multiple logins with wrong passwords
        String firstErrorMessage = null;
        for (int i = 0; i < 5; i++) {
            String wrongPassword = generateDifferentPassword(password);
            AuthenticationResult result = authService.login(username, wrongPassword);
            
            assertFalse(result.isSuccessful(), 
                "Login attempt " + i + " should fail");
            
            if (firstErrorMessage == null) {
                firstErrorMessage = result.getErrorMessage();
            } else {
                // All error messages should be identical
                assertEquals(firstErrorMessage, result.getErrorMessage(), 
                    "Error messages should be consistent across multiple failed attempts");
            }
        }
    }
    
    /**
     * Property test: Error messages don't leak timing information through content
     * 
     * **Validates: Requirements 1.3, 5.5**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyErrorMessagesHaveConsistentLength() {
        // Generate random credentials
        String existingUsername = generateRandomUsername();
        String nonExistentUsername = generateRandomUsername();
        String password = generateRandomPassword();
        
        // Create and save user
        String hashedPassword = passwordHasher.hashPassword(password);
        User user = new User(existingUsername, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Get error messages for both scenarios
        AuthenticationResult result1 = authService.login(existingUsername, password + "wrong");
        AuthenticationResult result2 = authService.login(nonExistentUsername, password);
        
        // Error messages should have the same length (same message)
        assertEquals(result1.getErrorMessage().length(), result2.getErrorMessage().length(), 
            "Error messages should have consistent length to avoid information leakage");
    }
    
    /**
     * Property test: Error messages are generic and don't contain sensitive information
     * 
     * **Validates: Requirements 1.3, 5.5**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyErrorMessagesAreGeneric() {
        // Generate random credentials
        String username = generateRandomUsername();
        String password = generateRandomPassword();
        
        // Attempt login with non-existent user
        AuthenticationResult result = authService.login(username, password);
        
        assertFalse(result.isSuccessful(), 
            "Login should fail for non-existent user");
        
        String errorMsg = result.getErrorMessage();
        
        // Error message should not contain specific details
        assertFalse(errorMsg.contains("database"), 
            "Error message should not mention database");
        assertFalse(errorMsg.contains("repository"), 
            "Error message should not mention repository");
        assertFalse(errorMsg.contains("hash"), 
            "Error message should not mention hash");
        assertFalse(errorMsg.contains("bcrypt"), 
            "Error message should not mention BCrypt");
        assertFalse(errorMsg.contains("not found"), 
            "Error message should not say 'not found'");
        assertFalse(errorMsg.contains("does not exist"), 
            "Error message should not say 'does not exist'");
    }
    
    /**
     * Property test: Successful login doesn't leak information in error field
     * 
     * **Validates: Requirements 1.3, 5.5**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertySuccessfulLoginHasNoErrorMessage() {
        // Generate random credentials
        String username = generateRandomUsername();
        String password = generateRandomPassword();
        
        // Create and save user
        String hashedPassword = passwordHasher.hashPassword(password);
        User user = new User(username, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Attempt successful login
        AuthenticationResult result = authService.login(username, password);
        
        assertTrue(result.isSuccessful(), 
            "Login should succeed with correct credentials");
        
        // Error message should be null for successful login
        assertNull(result.getErrorMessage(), 
            "Successful login should not have an error message");
    }
    
    /**
     * Property test: Error messages don't reveal password complexity requirements
     * 
     * **Validates: Requirements 1.3, 5.5**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyErrorMessagesDontRevealPasswordRequirements() {
        // Generate random credentials
        String username = generateRandomUsername();
        String password = generateRandomPassword();
        
        // Create and save user
        String hashedPassword = passwordHasher.hashPassword(password);
        User user = new User(username, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Try various wrong passwords
        String[] wrongPasswords = {
            "short",                    // Too short
            password + "x",             // Almost correct
            password.toUpperCase(),     // Wrong case
            ""                          // Empty (will throw exception, tested separately)
        };
        
        for (int i = 0; i < wrongPasswords.length - 1; i++) { // Skip empty password
            String wrongPassword = wrongPasswords[i];
            if (wrongPassword.equals(password)) {
                continue; // Skip if accidentally correct
            }
            
            AuthenticationResult result = authService.login(username, wrongPassword);
            
            String errorMsg = result.getErrorMessage().toLowerCase();
            
            // Error message should not reveal password requirements
            assertFalse(errorMsg.contains("length"), 
                "Error message should not mention password length");
            assertFalse(errorMsg.contains("character"), 
                "Error message should not mention character requirements");
            assertFalse(errorMsg.contains("complexity"), 
                "Error message should not mention complexity");
            assertFalse(errorMsg.contains("requirement"), 
                "Error message should not mention requirements");
        }
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
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+-=[]{}|;:,.<>?";
        
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        return password.toString();
    }
    
    private String generateDifferentPassword(String originalPassword) {
        String differentPassword;
        do {
            differentPassword = generateRandomPassword();
        } while (differentPassword.equals(originalPassword));
        
        return differentPassword;
    }
}
