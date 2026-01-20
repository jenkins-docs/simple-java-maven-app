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
 * Property-based tests for session expiration.
 * 
 * Feature: user-authentication
 * Property 10: Session Expiration
 * 
 * **Validates: Requirements 4.5**
 * 
 * For any session that has exceeded its expiration time, it should be 
 * automatically invalidated and no longer provide authentication.
 */
public class SessionExpirationPropertyTest {
    
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
     * Property 10: Session Expiration
     * 
     * For any session that has exceeded its expiration time, it should be 
     * automatically invalidated and no longer provide authentication.
     * 
     * **Validates: Requirements 4.5**
     * 
     * This property test verifies:
     * - Expired sessions are automatically invalidated
     * - Expired sessions do not provide authentication
     * - isAuthenticated returns false for expired sessions
     * - getCurrentUser returns null for expired sessions
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyExpiredSessionsAreInvalidated() {
        // Generate random username
        String username = generateRandomUsername();
        
        // Create an expired session directly in the repository
        String sessionId = generateRandomSessionId();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime pastTime = now.minusMinutes(random.nextInt(60) + 1); // 1-60 minutes ago
        LocalDateTime expiresAt = now.minusMinutes(random.nextInt(30) + 1); // Expired 1-30 minutes ago
        
        Session expiredSession = new Session(sessionId, username, pastTime, expiresAt);
        sessionRepository.createSession(expiredSession);
        
        // Verify the session is expired
        assertTrue(expiredSession.isExpired(), 
            "Session should be expired for user: " + username);
        
        // Property 1: Expired session should not be valid (Requirement 4.5)
        assertFalse(sessionRepository.isValid(sessionId), 
            "Expired session should not be valid for user: " + username);
        
        // Property 2: isAuthenticated should return false for expired session
        assertFalse(authService.isAuthenticated(sessionId), 
            "isAuthenticated should return false for expired session for user: " + username);
        
        // Property 3: getCurrentUser should return null for expired session
        assertNull(authService.getCurrentUser(sessionId), 
            "getCurrentUser should return null for expired session for user: " + username);
    }
    
    /**
     * Property test: Valid sessions are not affected by expiration checks
     * 
     * **Validates: Requirements 4.5**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyValidSessionsNotAffectedByExpirationChecks() {
        // Generate random valid credentials and create user
        String username = generateRandomUsername();
        String password = generateRandomPassword();
        String hashedPassword = passwordHasher.hashPassword(password);
        User user = new User(username, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Login to create a valid session
        AuthenticationResult loginResult = authService.login(username, password);
        String sessionId = loginResult.getSessionId();
        
        // Verify session is valid
        assertTrue(sessionRepository.isValid(sessionId), 
            "Session should be valid for user: " + username);
        
        // Property: Valid session should remain valid after expiration checks
        assertTrue(authService.isAuthenticated(sessionId), 
            "isAuthenticated should return true for valid session for user: " + username);
        
        String currentUser = authService.getCurrentUser(sessionId);
        assertNotNull(currentUser, 
            "getCurrentUser should return username for valid session");
        assertEquals(username, currentUser, 
            "getCurrentUser should return correct username for valid session");
        
        // Session should still be valid after multiple checks
        assertTrue(authService.isAuthenticated(sessionId), 
            "Session should remain valid after multiple checks");
    }
    
    /**
     * Property test: Cleanup removes expired sessions
     * 
     * **Validates: Requirements 4.5**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyCleanupRemovesExpiredSessions() {
        // Create multiple expired sessions
        int numExpiredSessions = random.nextInt(5) + 1; // 1-5 expired sessions
        String[] expiredSessionIds = new String[numExpiredSessions];
        
        for (int i = 0; i < numExpiredSessions; i++) {
            String username = generateRandomUsername();
            String sessionId = generateRandomSessionId();
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime pastTime = now.minusMinutes(random.nextInt(60) + 1);
            LocalDateTime expiresAt = now.minusMinutes(random.nextInt(30) + 1);
            
            Session expiredSession = new Session(sessionId, username, pastTime, expiresAt);
            sessionRepository.createSession(expiredSession);
            expiredSessionIds[i] = sessionId;
        }
        
        // Create a valid session
        String validUsername = generateRandomUsername();
        String validPassword = generateRandomPassword();
        String hashedPassword = passwordHasher.hashPassword(validPassword);
        User user = new User(validUsername, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        AuthenticationResult loginResult = authService.login(validUsername, validPassword);
        String validSessionId = loginResult.getSessionId();
        
        // Trigger cleanup by calling isAuthenticated
        authService.isAuthenticated(validSessionId);
        
        // Property: Expired sessions should be removed after cleanup
        for (String expiredSessionId : expiredSessionIds) {
            assertFalse(sessionRepository.findBySessionId(expiredSessionId).isPresent(), 
                "Expired session should be removed after cleanup: " + expiredSessionId);
        }
        
        // Valid session should still exist
        assertTrue(sessionRepository.findBySessionId(validSessionId).isPresent(), 
            "Valid session should not be removed by cleanup");
        assertTrue(authService.isAuthenticated(validSessionId), 
            "Valid session should remain authenticated after cleanup");
    }
    
    /**
     * Property test: Sessions expire at the correct time
     * 
     * **Validates: Requirements 4.5**
     */
    @Test
    public void propertySessionsExpireAtCorrectTime() {
        // Generate random valid credentials and create user
        String username = generateRandomUsername();
        String password = generateRandomPassword();
        String hashedPassword = passwordHasher.hashPassword(password);
        User user = new User(username, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
        
        // Login to create a session
        AuthenticationResult loginResult = authService.login(username, password);
        String sessionId = loginResult.getSessionId();
        
        // Get the session to check expiration time
        Session session = sessionRepository.findBySessionId(sessionId).orElse(null);
        assertNotNull(session, "Session should exist");
        
        // Session should not be expired immediately after creation
        assertFalse(session.isExpired(), 
            "Session should not be expired immediately after creation");
        assertTrue(authService.isAuthenticated(sessionId), 
            "Session should be authenticated immediately after creation");
        
        // Verify expiration time is in the future
        assertTrue(session.getExpiresAt().isAfter(LocalDateTime.now()), 
            "Session expiration time should be in the future");
    }
    
    /**
     * Property test: Multiple expired sessions are all invalidated
     * 
     * **Validates: Requirements 4.5**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyMultipleExpiredSessionsAllInvalidated() {
        // Create multiple users with expired sessions
        int numUsers = random.nextInt(5) + 2; // 2-6 users
        String[] sessionIds = new String[numUsers];
        String[] usernames = new String[numUsers];
        
        for (int i = 0; i < numUsers; i++) {
            String username = generateRandomUsername();
            usernames[i] = username;
            
            String sessionId = generateRandomSessionId();
            sessionIds[i] = sessionId;
            
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime pastTime = now.minusMinutes(random.nextInt(60) + 1);
            LocalDateTime expiresAt = now.minusMinutes(random.nextInt(30) + 1);
            
            Session expiredSession = new Session(sessionId, username, pastTime, expiresAt);
            sessionRepository.createSession(expiredSession);
        }
        
        // Property: All expired sessions should be invalid
        for (int i = 0; i < numUsers; i++) {
            assertFalse(sessionRepository.isValid(sessionIds[i]), 
                "Expired session should be invalid for user: " + usernames[i]);
            assertFalse(authService.isAuthenticated(sessionIds[i]), 
                "isAuthenticated should return false for expired session for user: " + usernames[i]);
            assertNull(authService.getCurrentUser(sessionIds[i]), 
                "getCurrentUser should return null for expired session for user: " + usernames[i]);
        }
    }
    
    /**
     * Property test: Expired sessions cannot be used for authentication
     * 
     * **Validates: Requirements 4.5**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    public void propertyExpiredSessionsCannotBeUsedForAuthentication() {
        // Generate random username
        String username = generateRandomUsername();
        
        // Create an expired session
        String sessionId = generateRandomSessionId();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime pastTime = now.minusHours(2);
        LocalDateTime expiresAt = now.minusHours(1); // Expired 1 hour ago
        
        Session expiredSession = new Session(sessionId, username, pastTime, expiresAt);
        sessionRepository.createSession(expiredSession);
        
        // Property 1: Expired session should not provide authentication
        assertFalse(authService.isAuthenticated(sessionId), 
            "Expired session should not provide authentication for user: " + username);
        
        // Property 2: Cannot get user from expired session
        assertNull(authService.getCurrentUser(sessionId), 
            "Cannot get user from expired session for user: " + username);
        
        // Property 3: Session should not be valid
        assertFalse(sessionRepository.isValid(sessionId), 
            "Expired session should not be valid for user: " + username);
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
