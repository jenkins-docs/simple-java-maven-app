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
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Property-based tests for invalid credentials rejection.
 * 
 * Feature: user-authentication
 * Property 2: Invalid Credentials Rejected
 * 
 * **Validates: Requirements 1.2, 1.3**
 * 
 * For any invalid credential combination (wrong password, non-existent user, 
 * malformed input), login attempts should be rejected with appropriate error handling.
 */
public class InvalidCredentialsPropertyTest {
    
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
     * Property 2: Invalid Credentials Rejected - Wrong Password
     * 
     * For any valid user with wrong password, login should be rejected.
     * 
     * **Validates: Requirements 1.2**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyInvalidPasswordRejected() {
        // Generate random credentials
        String username = generateRandomUsername();
        String correctPassword = generateRandomPassword();
        String wrongPassword = generateDifferentPassword(correctPassword);
        
        // Create and save user with correct password
        String hashedPassword = passwordHasher.hashPassword(correctPassword);
        User user = new User(username, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Attempt login with wrong password
        AuthenticationResult result = authService.login(username, wrongPassword);
        
        // Property 1: Login should fail
        assertFalse(result.isSuccessful(), 
            "Login with wrong password should fail for user: " + username);
        
        // Property 2: No session ID should be returned
        assertNull(result.getSessionId(), 
            "Failed login should not return a session ID for user: " + username);
        
        // Property 3: Error message should be present
        assertNotNull(result.getErrorMessage(), 
            "Failed login should return an error message for user: " + username);
        assertFalse(result.getErrorMessage().isEmpty(), 
            "Error message should not be empty for user: " + username);
    }
    
    /**
     * Property 2: Invalid Credentials Rejected - Non-existent User
     * 
     * For any non-existent username, login should be rejected.
     * 
     * **Validates: Requirements 1.2, 1.3**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyNonExistentUserRejected() {
        // Generate random credentials for non-existent user
        String username = generateRandomUsername();
        String password = generateRandomPassword();
        
        // Do NOT create the user - attempt login with non-existent user
        AuthenticationResult result = authService.login(username, password);
        
        // Property 1: Login should fail
        assertFalse(result.isSuccessful(), 
            "Login with non-existent user should fail for: " + username);
        
        // Property 2: No session ID should be returned
        assertNull(result.getSessionId(), 
            "Failed login should not return a session ID for non-existent user: " + username);
        
        // Property 3: Error message should be present
        assertNotNull(result.getErrorMessage(), 
            "Failed login should return an error message for non-existent user: " + username);
        assertFalse(result.getErrorMessage().isEmpty(), 
            "Error message should not be empty for non-existent user: " + username);
    }
    
    /**
     * Property 2: Invalid Credentials Rejected - Empty Password
     * 
     * For any user with empty password, login should throw exception.
     * 
     * **Validates: Requirements 1.2**
     */
    @Test
    public void propertyEmptyPasswordThrowsException() {
        String username = generateRandomUsername();
        
        // Attempt login with empty password should throw exception
        assertThrows(IllegalArgumentException.class, () -> {
            authService.login(username, "");
        }, "Login with empty password should throw IllegalArgumentException");
    }
    
    /**
     * Property 2: Invalid Credentials Rejected - Null Password
     * 
     * For any user with null password, login should throw exception.
     * 
     * **Validates: Requirements 1.2**
     */
    @Test
    public void propertyNullPasswordThrowsException() {
        String username = generateRandomUsername();
        
        // Attempt login with null password should throw exception
        assertThrows(IllegalArgumentException.class, () -> {
            authService.login(username, null);
        }, "Login with null password should throw IllegalArgumentException");
    }
    
    /**
     * Property 2: Invalid Credentials Rejected - Empty Username
     * 
     * For any empty username, login should throw exception.
     * 
     * **Validates: Requirements 1.2**
     */
    @Test
    public void propertyEmptyUsernameThrowsException() {
        String password = generateRandomPassword();
        
        // Attempt login with empty username should throw exception
        assertThrows(IllegalArgumentException.class, () -> {
            authService.login("", password);
        }, "Login with empty username should throw IllegalArgumentException");
        
        // Also test with whitespace-only username
        assertThrows(IllegalArgumentException.class, () -> {
            authService.login("   ", password);
        }, "Login with whitespace-only username should throw IllegalArgumentException");
    }
    
    /**
     * Property 2: Invalid Credentials Rejected - Null Username
     * 
     * For any null username, login should throw exception.
     * 
     * **Validates: Requirements 1.2**
     */
    @Test
    public void propertyNullUsernameThrowsException() {
        String password = generateRandomPassword();
        
        // Attempt login with null username should throw exception
        assertThrows(IllegalArgumentException.class, () -> {
            authService.login(null, password);
        }, "Login with null username should throw IllegalArgumentException");
    }
    
    /**
     * Property test: Multiple failed login attempts don't create sessions
     * 
     * **Validates: Requirements 1.2**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyMultipleFailedLoginsCreateNoSessions() {
        // Generate random credentials
        String username = generateRandomUsername();
        String correctPassword = generateRandomPassword();
        
        // Create and save user
        String hashedPassword = passwordHasher.hashPassword(correctPassword);
        User user = new User(username, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Attempt multiple logins with wrong passwords
        for (int i = 0; i < 5; i++) {
            String wrongPassword = generateDifferentPassword(correctPassword);
            AuthenticationResult result = authService.login(username, wrongPassword);
            
            // Each attempt should fail
            assertFalse(result.isSuccessful(), 
                "Login attempt " + i + " with wrong password should fail for user: " + username);
            assertNull(result.getSessionId(), 
                "Failed login attempt " + i + " should not create session for user: " + username);
        }
    }
    
    /**
     * Property test: Invalid credentials with various password variations
     * 
     * **Validates: Requirements 1.2**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyVariousInvalidPasswordsRejected() {
        String username = generateRandomUsername();
        String correctPassword = generateRandomPassword();
        
        // Create and save user
        String hashedPassword = passwordHasher.hashPassword(correctPassword);
        User user = new User(username, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Try various wrong password variations
        String[] wrongPasswords = {
            correctPassword + "x",           // Append character
            "x" + correctPassword,           // Prepend character
            correctPassword.toUpperCase(),   // Change case
            correctPassword.toLowerCase(),   // Change case
            correctPassword.substring(0, Math.max(1, correctPassword.length() - 1)) // Truncate
        };
        
        for (String wrongPassword : wrongPasswords) {
            // Skip if we accidentally created the correct password
            if (wrongPassword.equals(correctPassword)) {
                continue;
            }
            
            AuthenticationResult result = authService.login(username, wrongPassword);
            
            assertFalse(result.isSuccessful(), 
                "Login with modified password should fail for user: " + username);
            assertNull(result.getSessionId(), 
                "Failed login should not create session for user: " + username);
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
