package com.mycompany.app.auth.service;

import com.mycompany.app.auth.model.Session;
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
 * Property-based tests for session invalidation on logout.
 * 
 * Feature: user-authentication
 * Property 4: Session Invalidation on Logout
 * 
 * **Validates: Requirements 2.1, 2.2, 2.4**
 * 
 * For any authenticated session, logout should invalidate the session and 
 * prevent further access using that session identifier.
 */
public class SessionInvalidationPropertyTest {
    
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
     * Property 4: Session Invalidation on Logout
     * 
     * For any authenticated session, logout should invalidate the session and 
     * prevent further access using that session identifier.
     * 
     * **Validates: Requirements 2.1, 2.2, 2.4**
     * 
     * This property test verifies:
     * - Requirement 2.1: Logout invalidates the current session
     * - Requirement 2.2: Invalidated sessions prevent further access
     * - Requirement 2.4: All session data is cleared upon logout
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertySessionInvalidationOnLogout() {
        // Generate random valid credentials and create user
        String username = generateRandomUsername();
        String password = generateRandomPassword();
        String hashedPassword = passwordHasher.hashPassword(password);
        User user = new User(username, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Login to create an authenticated session
        AuthenticationResult loginResult = authService.login(username, password);
        assertTrue(loginResult.isSuccessful(), 
            "Login should succeed for user: " + username);
        
        String sessionId = loginResult.getSessionId();
        assertNotNull(sessionId, 
            "Session ID should be created for user: " + username);
        
        // Verify session is valid before logout
        assertTrue(sessionRepository.isValid(sessionId), 
            "Session should be valid before logout for user: " + username);
        assertTrue(sessionRepository.getUser(sessionId).isPresent(), 
            "Session should be associated with user before logout: " + username);
        
        // Property 1: Logout invalidates the session (Requirement 2.1)
        authService.logout(sessionId);
        
        // Property 2: Invalidated session prevents further access (Requirement 2.2)
        assertFalse(sessionRepository.isValid(sessionId), 
            "Session should be invalid after logout for user: " + username);
        
        // Property 3: All session data is cleared (Requirement 2.4)
        assertFalse(sessionRepository.getUser(sessionId).isPresent(), 
            "Session data should be cleared after logout for user: " + username);
        assertFalse(sessionRepository.findBySessionId(sessionId).isPresent(), 
            "Session should not exist in repository after logout for user: " + username);
    }
    
    /**
     * Property test: Logout is idempotent - multiple logout calls don't cause errors
     * 
     * **Validates: Requirements 2.1, 2.3**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyLogoutIsIdempotent() {
        // Generate random valid credentials and create user
        String username = generateRandomUsername();
        String password = generateRandomPassword();
        String hashedPassword = passwordHasher.hashPassword(password);
        User user = new User(username, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Login to create an authenticated session
        AuthenticationResult loginResult = authService.login(username, password);
        String sessionId = loginResult.getSessionId();
        
        // First logout
        authService.logout(sessionId);
        assertFalse(sessionRepository.isValid(sessionId), 
            "Session should be invalid after first logout");
        
        // Second logout should not throw exception (Requirement 2.3)
        assertDoesNotThrow(() -> authService.logout(sessionId), 
            "Multiple logout calls should not throw exceptions");
        
        // Third logout should also not throw exception
        assertDoesNotThrow(() -> authService.logout(sessionId), 
            "Multiple logout calls should be handled gracefully");
        
        // Session should still be invalid
        assertFalse(sessionRepository.isValid(sessionId), 
            "Session should remain invalid after multiple logout calls");
    }
    
    /**
     * Property test: Logout with non-existent session ID is handled gracefully
     * 
     * **Validates: Requirements 2.3**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyLogoutHandlesNonExistentSessionGracefully() {
        // Generate random session ID that doesn't exist
        String nonExistentSessionId = generateRandomSessionId();
        
        // Verify session doesn't exist
        assertFalse(sessionRepository.isValid(nonExistentSessionId), 
            "Generated session ID should not exist");
        
        // Logout should not throw exception for non-existent session (Requirement 2.3)
        assertDoesNotThrow(() -> authService.logout(nonExistentSessionId), 
            "Logout should handle non-existent sessions gracefully");
        
        // Session should still not exist
        assertFalse(sessionRepository.isValid(nonExistentSessionId), 
            "Non-existent session should remain non-existent");
    }
    
    /**
     * Property test: Logout only affects the specified session
     * 
     * **Validates: Requirements 2.1, 2.2**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyLogoutOnlyAffectsSpecifiedSession() {
        // Create two users with different credentials
        String username1 = generateRandomUsername();
        String password1 = generateRandomPassword();
        String hashedPassword1 = passwordHasher.hashPassword(password1);
        User user1 = new User(username1, hashedPassword1, LocalDateTime.now(), null);
        userRepository.save(user1);
        
        String username2 = generateRandomUsername();
        String password2 = generateRandomPassword();
        String hashedPassword2 = passwordHasher.hashPassword(password2);
        User user2 = new User(username2, hashedPassword2, LocalDateTime.now(), null);
        userRepository.save(user2);
        
        // Login both users to create two sessions
        AuthenticationResult loginResult1 = authService.login(username1, password1);
        AuthenticationResult loginResult2 = authService.login(username2, password2);
        
        String sessionId1 = loginResult1.getSessionId();
        String sessionId2 = loginResult2.getSessionId();
        
        // Verify both sessions are valid
        assertTrue(sessionRepository.isValid(sessionId1), 
            "First session should be valid");
        assertTrue(sessionRepository.isValid(sessionId2), 
            "Second session should be valid");
        
        // Logout first user
        authService.logout(sessionId1);
        
        // First session should be invalid
        assertFalse(sessionRepository.isValid(sessionId1), 
            "First session should be invalid after logout");
        
        // Second session should still be valid
        assertTrue(sessionRepository.isValid(sessionId2), 
            "Second session should remain valid after first user logout");
        assertTrue(sessionRepository.getUser(sessionId2).isPresent(), 
            "Second session should still be associated with user");
        assertEquals(username2, sessionRepository.getUser(sessionId2).get(), 
            "Second session should still be associated with correct user");
    }
    
    /**
     * Property test: Logout clears all session data completely
     * 
     * **Validates: Requirements 2.4**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyLogoutClearsAllSessionData() {
        // Generate random valid credentials and create user
        String username = generateRandomUsername();
        String password = generateRandomPassword();
        String hashedPassword = passwordHasher.hashPassword(password);
        User user = new User(username, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Login to create an authenticated session
        AuthenticationResult loginResult = authService.login(username, password);
        String sessionId = loginResult.getSessionId();
        
        // Verify session data exists before logout
        assertTrue(sessionRepository.findBySessionId(sessionId).isPresent(), 
            "Session should exist before logout");
        Session sessionBeforeLogout = sessionRepository.findBySessionId(sessionId).get();
        assertNotNull(sessionBeforeLogout.getSessionId(), 
            "Session ID should exist before logout");
        assertNotNull(sessionBeforeLogout.getUsername(), 
            "Username should exist before logout");
        
        // Logout
        authService.logout(sessionId);
        
        // Verify all session data is cleared (Requirement 2.4)
        assertFalse(sessionRepository.findBySessionId(sessionId).isPresent(), 
            "Session should not exist after logout");
        assertFalse(sessionRepository.getUser(sessionId).isPresent(), 
            "User association should be cleared after logout");
        assertFalse(sessionRepository.isValid(sessionId), 
            "Session validity should be false after logout");
    }
    
    /**
     * Property test: User can login again after logout
     * 
     * **Validates: Requirements 2.1, 2.2**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyUserCanLoginAgainAfterLogout() {
        // Generate random valid credentials and create user
        String username = generateRandomUsername();
        String password = generateRandomPassword();
        String hashedPassword = passwordHasher.hashPassword(password);
        User user = new User(username, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // First login
        AuthenticationResult loginResult1 = authService.login(username, password);
        String sessionId1 = loginResult1.getSessionId();
        assertTrue(sessionRepository.isValid(sessionId1), 
            "First session should be valid");
        
        // Logout
        authService.logout(sessionId1);
        assertFalse(sessionRepository.isValid(sessionId1), 
            "First session should be invalid after logout");
        
        // Second login should succeed
        AuthenticationResult loginResult2 = authService.login(username, password);
        assertTrue(loginResult2.isSuccessful(), 
            "User should be able to login again after logout");
        
        String sessionId2 = loginResult2.getSessionId();
        assertNotNull(sessionId2, 
            "Second login should create a new session");
        assertTrue(sessionRepository.isValid(sessionId2), 
            "Second session should be valid");
        
        // New session should be different from old session
        assertNotEquals(sessionId1, sessionId2, 
            "New login should create a different session ID");
        
        // Old session should still be invalid
        assertFalse(sessionRepository.isValid(sessionId1), 
            "Old session should remain invalid");
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
    
    private String generateRandomSessionId() {
        return java.util.UUID.randomUUID().toString();
    }
}
