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
 * Property-based tests for valid login functionality.
 * 
 * Feature: user-authentication
 * Property 1: Valid Login Creates Session
 * 
 * **Validates: Requirements 1.1**
 * 
 * For any valid user credentials, successful login should create an authenticated 
 * session that can be used to verify the user's authentication status.
 */
public class ValidLoginPropertyTest {
    
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
     * Property 1: Valid Login Creates Session
     * 
     * For any valid user credentials, successful login should create an authenticated 
     * session that can be used to verify the user's authentication status.
     * 
     * **Validates: Requirements 1.1**
     * 
     * This property test verifies:
     * - Login with valid credentials returns successful result
     * - A session ID is generated and returned
     * - The session can be used to verify authentication
     * - The session is associated with the correct user
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyValidLoginCreatesSession() {
        // Generate random valid credentials
        String username = generateRandomUsername();
        String password = generateRandomPassword();
        
        // Create and save user with hashed password
        String hashedPassword = passwordHasher.hashPassword(password);
        User user = new User(username, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Attempt login with valid credentials
        AuthenticationResult result = authService.login(username, password);
        
        // Property 1: Login should succeed
        assertTrue(result.isSuccessful(), 
            "Login with valid credentials should succeed for user: " + username);
        
        // Property 2: Result should contain a session ID
        assertNotNull(result.getSessionId(), 
            "Successful login should return a session ID for user: " + username);
        assertFalse(result.getSessionId().isEmpty(), 
            "Session ID should not be empty for user: " + username);
        
        // Property 3: Session should be valid and authenticated
        String sessionId = result.getSessionId();
        assertTrue(sessionRepository.isValid(sessionId), 
            "Created session should be valid for user: " + username);
        
        // Property 4: Session should be associated with the correct user
        assertTrue(sessionRepository.getUser(sessionId).isPresent(), 
            "Session should be associated with a user: " + username);
        assertEquals(username, sessionRepository.getUser(sessionId).get(), 
            "Session should be associated with the correct user: " + username);
        
        // Property 5: No error message should be present on success
        assertNull(result.getErrorMessage(), 
            "Successful login should not have an error message for user: " + username);
    }
    
    /**
     * Property test: Multiple valid logins for the same user create different sessions
     * 
     * **Validates: Requirements 1.1**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyMultipleLoginsCreateDifferentSessions() {
        // Generate random valid credentials
        String username = generateRandomUsername();
        String password = generateRandomPassword();
        
        // Create and save user
        String hashedPassword = passwordHasher.hashPassword(password);
        User user = new User(username, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Perform two logins with the same credentials
        AuthenticationResult result1 = authService.login(username, password);
        AuthenticationResult result2 = authService.login(username, password);
        
        // Both logins should succeed
        assertTrue(result1.isSuccessful(), 
            "First login should succeed for user: " + username);
        assertTrue(result2.isSuccessful(), 
            "Second login should succeed for user: " + username);
        
        // Session IDs should be different
        assertNotEquals(result1.getSessionId(), result2.getSessionId(), 
            "Multiple logins should create different session IDs for user: " + username);
        
        // Both sessions should be valid
        assertTrue(sessionRepository.isValid(result1.getSessionId()), 
            "First session should be valid for user: " + username);
        assertTrue(sessionRepository.isValid(result2.getSessionId()), 
            "Second session should be valid for user: " + username);
    }
    
    /**
     * Property test: Valid login with various password types
     * 
     * **Validates: Requirements 1.1**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyValidLoginWorksWithVariousPasswordTypes() {
        String username = generateRandomUsername();
        
        // Generate different types of passwords
        String password;
        int passwordType = random.nextInt(5);
        switch (passwordType) {
            case 0: // Simple alphanumeric
                password = generateSimplePassword();
                break;
            case 1: // With special characters
                password = generatePasswordWithSpecialChars();
                break;
            case 2: // Long password
                password = generateLongPassword();
                break;
            case 3: // Short password
                password = generateShortPassword();
                break;
            default: // Random password
                password = generateRandomPassword();
                break;
        }
        
        // Create and save user
        String hashedPassword = passwordHasher.hashPassword(password);
        User user = new User(username, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Login should succeed regardless of password type
        AuthenticationResult result = authService.login(username, password);
        
        assertTrue(result.isSuccessful(), 
            "Login should succeed with password type " + passwordType + " for user: " + username);
        assertNotNull(result.getSessionId(), 
            "Session ID should be created for password type " + passwordType);
        assertTrue(sessionRepository.isValid(result.getSessionId()), 
            "Session should be valid for password type " + passwordType);
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
    
    private String generateSimplePassword() {
        int length = 8 + random.nextInt(9); // Length between 8 and 16
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
    
    private String generateLongPassword() {
        int length = 30 + random.nextInt(41); // Length between 30 and 70
        StringBuilder password = new StringBuilder();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        return password.toString();
    }
    
    private String generateShortPassword() {
        int length = 8 + random.nextInt(3); // Length between 8 and 10
        StringBuilder password = new StringBuilder();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        return password.toString();
    }
}
