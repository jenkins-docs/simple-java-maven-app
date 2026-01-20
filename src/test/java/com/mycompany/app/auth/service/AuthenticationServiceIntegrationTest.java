package com.mycompany.app.auth.service;

import com.mycompany.app.auth.model.PasswordResetToken;
import com.mycompany.app.auth.model.Session;
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
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for AuthenticationServiceImpl.
 * 
 * These tests verify complete authentication flows including:
 * - Login-logout cycles
 * - Password reset flows
 * - Session management across multiple operations
 * 
 * Tests use real implementations (not mocks) to verify integration between components.
 */
public class AuthenticationServiceIntegrationTest {
    
    private AuthenticationService authService;
    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private PasswordResetTokenRepository tokenRepository;
    private PasswordHasher passwordHasher;
    
    @BeforeEach
    public void setUp() {
        // Create real implementations for integration testing
        userRepository = new InMemoryUserRepository();
        sessionRepository = new InMemorySessionRepository();
        tokenRepository = new InMemoryPasswordResetTokenRepository();
        passwordHasher = new PasswordHasher();
        SecureIdentifierGenerator identifierGenerator = new SecureIdentifierGenerator();
        
        // Create the service with all dependencies
        authService = new AuthenticationServiceImpl(
            userRepository,
            sessionRepository,
            tokenRepository,
            passwordHasher,
            identifierGenerator
        );
        
        // Create test users
        createTestUser("alice", "Password123");
        createTestUser("bob", "SecurePass456");
    }
    
    private void createTestUser(String username, String password) {
        String hashedPassword = passwordHasher.hashPassword(password);
        User user = new User(username, hashedPassword, LocalDateTime.now(), null);
        userRepository.save(user);
    }
    
    /**
     * Test complete login-logout cycle.
     * Validates: Requirements 1.1, 2.1, 2.2, 2.4
     */
    @Test
    public void testCompleteLoginLogoutCycle() {
        // Login with valid credentials
        AuthenticationResult loginResult = authService.login("alice", "Password123");
        
        assertTrue(loginResult.isSuccessful(), "Login should succeed with valid credentials");
        assertNotNull(loginResult.getSessionId(), "Session ID should be provided");
        assertNull(loginResult.getErrorMessage(), "No error message for successful login");
        
        String sessionId = loginResult.getSessionId();
        
        // Verify user is authenticated
        assertTrue(authService.isAuthenticated(sessionId), "User should be authenticated after login");
        assertEquals("alice", authService.getCurrentUser(sessionId), "Current user should be alice");
        
        // Logout
        authService.logout(sessionId);
        
        // Verify user is no longer authenticated
        assertFalse(authService.isAuthenticated(sessionId), "User should not be authenticated after logout");
        assertNull(authService.getCurrentUser(sessionId), "Current user should be null after logout");
    }
    
    /**
     * Test multiple concurrent sessions for different users.
     * Validates: Requirements 4.1, 4.2, 4.3
     */
    @Test
    public void testMultipleConcurrentSessions() {
        // Login alice
        AuthenticationResult aliceLogin = authService.login("alice", "Password123");
        assertTrue(aliceLogin.isSuccessful());
        String aliceSessionId = aliceLogin.getSessionId();
        
        // Login bob
        AuthenticationResult bobLogin = authService.login("bob", "SecurePass456");
        assertTrue(bobLogin.isSuccessful());
        String bobSessionId = bobLogin.getSessionId();
        
        // Verify both sessions are independent
        assertNotEquals(aliceSessionId, bobSessionId, "Session IDs should be unique");
        
        // Verify both users are authenticated
        assertTrue(authService.isAuthenticated(aliceSessionId));
        assertTrue(authService.isAuthenticated(bobSessionId));
        
        assertEquals("alice", authService.getCurrentUser(aliceSessionId));
        assertEquals("bob", authService.getCurrentUser(bobSessionId));
        
        // Logout alice
        authService.logout(aliceSessionId);
        
        // Verify alice is logged out but bob is still authenticated
        assertFalse(authService.isAuthenticated(aliceSessionId));
        assertTrue(authService.isAuthenticated(bobSessionId));
        assertEquals("bob", authService.getCurrentUser(bobSessionId));
        
        // Logout bob
        authService.logout(bobSessionId);
        assertFalse(authService.isAuthenticated(bobSessionId));
    }
    
    /**
     * Test complete password reset flow.
     * Validates: Requirements 3.1, 3.2, 3.3
     */
    @Test
    public void testCompletePasswordResetFlow() {
        // Initiate password reset
        PasswordResetResult resetResult = authService.initiatePasswordReset("alice");
        
        assertTrue(resetResult.isSuccessful(), "Password reset initiation should succeed");
        assertNotNull(resetResult.getToken(), "Reset token should be provided");
        assertNull(resetResult.getErrorMessage(), "No error message for successful reset");
        
        String resetToken = resetResult.getToken();
        
        // Try to login with old password - should succeed
        AuthenticationResult oldPasswordLogin = authService.login("alice", "Password123");
        assertTrue(oldPasswordLogin.isSuccessful(), "Old password should still work before reset");
        authService.logout(oldPasswordLogin.getSessionId());
        
        // Complete password reset with new password
        boolean resetComplete = authService.completePasswordReset(resetToken, "NewPassword789");
        assertTrue(resetComplete, "Password reset should complete successfully");
        
        // Try to login with old password - should fail
        AuthenticationResult oldPasswordFail = authService.login("alice", "Password123");
        assertFalse(oldPasswordFail.isSuccessful(), "Old password should not work after reset");
        
        // Try to login with new password - should succeed
        AuthenticationResult newPasswordLogin = authService.login("alice", "NewPassword789");
        assertTrue(newPasswordLogin.isSuccessful(), "New password should work after reset");
        assertNotNull(newPasswordLogin.getSessionId());
        
        // Verify token is invalidated after use (single-use)
        boolean tokenReuse = authService.completePasswordReset(resetToken, "AnotherPassword999");
        assertFalse(tokenReuse, "Token should not be reusable after successful reset");
    }
    
    /**
     * Test password reset with invalid token.
     * Validates: Requirements 3.3, 3.4
     */
    @Test
    public void testPasswordResetWithInvalidToken() {
        // Try to reset password with non-existent token
        boolean resetResult = authService.completePasswordReset("invalid-token-12345", "NewPassword789");
        assertFalse(resetResult, "Password reset should fail with invalid token");
        
        // Verify original password still works
        AuthenticationResult loginResult = authService.login("alice", "Password123");
        assertTrue(loginResult.isSuccessful(), "Original password should still work");
    }
    
    /**
     * Test password reset with expired token.
     * Validates: Requirements 3.4
     */
    @Test
    public void testPasswordResetWithExpiredToken() {
        // Create an expired token manually
        String expiredToken = "expired-token-12345";
        PasswordResetToken resetToken = new PasswordResetToken(
            expiredToken,
            "alice",
            LocalDateTime.now().minusHours(2),
            LocalDateTime.now().minusHours(1)
        );
        tokenRepository.save(resetToken);
        
        // Try to use expired token
        boolean resetResult = authService.completePasswordReset(expiredToken, "NewPassword789");
        assertFalse(resetResult, "Password reset should fail with expired token");
        
        // Verify original password still works
        AuthenticationResult loginResult = authService.login("alice", "Password123");
        assertTrue(loginResult.isSuccessful(), "Original password should still work");
    }
    
    /**
     * Test password validation during reset.
     * Validates: Requirements 3.5
     */
    @Test
    public void testPasswordValidationDuringReset() {
        // Initiate password reset
        PasswordResetResult resetResult = authService.initiatePasswordReset("alice");
        assertTrue(resetResult.isSuccessful());
        String resetToken = resetResult.getToken();
        
        // Try weak passwords
        assertFalse(authService.completePasswordReset(resetToken, "short"), 
            "Should reject password that's too short");
        assertFalse(authService.completePasswordReset(resetToken, "nouppercase123"), 
            "Should reject password without uppercase");
        assertFalse(authService.completePasswordReset(resetToken, "NOLOWERCASE123"), 
            "Should reject password without lowercase");
        assertFalse(authService.completePasswordReset(resetToken, "NoDigitsHere"), 
            "Should reject password without digits");
        
        // Token should still be valid after failed attempts
        assertTrue(authService.completePasswordReset(resetToken, "ValidPass123"), 
            "Should accept valid password");
    }
    
    /**
     * Test login with invalid credentials.
     * Validates: Requirements 1.2, 1.3
     */
    @Test
    public void testLoginWithInvalidCredentials() {
        // Wrong password
        AuthenticationResult wrongPassword = authService.login("alice", "WrongPassword");
        assertFalse(wrongPassword.isSuccessful());
        assertNotNull(wrongPassword.getErrorMessage());
        assertNull(wrongPassword.getSessionId());
        
        // Non-existent user
        AuthenticationResult nonExistent = authService.login("nonexistent", "Password123");
        assertFalse(nonExistent.isSuccessful());
        assertNotNull(nonExistent.getErrorMessage());
        
        // Verify error messages are generic (don't reveal user existence)
        assertEquals(wrongPassword.getErrorMessage(), nonExistent.getErrorMessage(),
            "Error messages should be generic and not reveal user existence");
    }
    
    /**
     * Test session expiration handling.
     * Validates: Requirements 4.5
     */
    @Test
    public void testSessionExpirationHandling() {
        // Create an expired session manually
        String expiredSessionId = "expired-session-12345";
        Session expiredSession = new Session(
            expiredSessionId,
            "alice",
            LocalDateTime.now().minusHours(1),
            LocalDateTime.now().minusMinutes(30)
        );
        sessionRepository.createSession(expiredSession);
        
        // Verify expired session is not valid
        assertFalse(authService.isAuthenticated(expiredSessionId), 
            "Expired session should not be authenticated");
        assertNull(authService.getCurrentUser(expiredSessionId), 
            "Expired session should not return current user");
    }
    
    /**
     * Test password reset for non-existent user.
     * Validates: Requirements 3.6, 5.5
     * 
     * Security Note: To prevent user enumeration attacks, the system returns
     * success with a fake token even for non-existent users. The fake token
     * will not work for password reset, but the response looks identical to
     * a real token response.
     */
    @Test
    public void testPasswordResetForNonExistentUser() {
        // Try to reset password for non-existent user
        PasswordResetResult resetResult = authService.initiatePasswordReset("nonexistent");
        
        // Should return success with fake token (don't reveal user existence)
        assertTrue(resetResult.isSuccessful(), "Should return success to prevent user enumeration");
        assertNotNull(resetResult.getToken(), "Should provide fake token");
        assertNull(resetResult.getErrorMessage(), "Should not have error message");
        
        // Verify the fake token doesn't actually work
        boolean resetComplete = authService.completePasswordReset(resetResult.getToken(), "NewPassword123");
        assertFalse(resetComplete, "Fake token should not work for password reset");
    }
    
    /**
     * Test input validation for all methods.
     * Validates: Requirements 1.4
     */
    @Test
    public void testInputValidation() {
        // Login validation
        assertThrows(IllegalArgumentException.class, () -> authService.login(null, "password"));
        assertThrows(IllegalArgumentException.class, () -> authService.login("", "password"));
        assertThrows(IllegalArgumentException.class, () -> authService.login("user", null));
        assertThrows(IllegalArgumentException.class, () -> authService.login("user", ""));
        
        // Logout validation
        assertThrows(IllegalArgumentException.class, () -> authService.logout(null));
        assertThrows(IllegalArgumentException.class, () -> authService.logout(""));
        
        // isAuthenticated validation
        assertThrows(IllegalArgumentException.class, () -> authService.isAuthenticated(null));
        assertThrows(IllegalArgumentException.class, () -> authService.isAuthenticated(""));
        
        // getCurrentUser validation
        assertThrows(IllegalArgumentException.class, () -> authService.getCurrentUser(null));
        assertThrows(IllegalArgumentException.class, () -> authService.getCurrentUser(""));
        
        // initiatePasswordReset validation
        assertThrows(IllegalArgumentException.class, () -> authService.initiatePasswordReset(null));
        assertThrows(IllegalArgumentException.class, () -> authService.initiatePasswordReset(""));
        
        // completePasswordReset validation - null/empty token throws exception
        assertThrows(IllegalArgumentException.class, () -> authService.completePasswordReset(null, "ValidPass123"));
        assertThrows(IllegalArgumentException.class, () -> authService.completePasswordReset("", "ValidPass123"));
        
        // completePasswordReset validation - null password throws exception
        assertThrows(IllegalArgumentException.class, () -> authService.completePasswordReset("token", null));
        
        // completePasswordReset validation - empty/whitespace password returns false (not exception)
        PasswordResetResult resetResult = authService.initiatePasswordReset("alice");
        String token = resetResult.getToken();
        assertFalse(authService.completePasswordReset(token, ""), 
            "Empty password should return false");
        assertFalse(authService.completePasswordReset(token, "   "), 
            "Whitespace-only password should return false");
    }
    
    /**
     * Test logout for non-authenticated user.
     * Validates: Requirements 2.3
     */
    @Test
    public void testLogoutForNonAuthenticatedUser() {
        // Logout with non-existent session should not throw exception
        assertDoesNotThrow(() -> authService.logout("non-existent-session"));
    }
    
    /**
     * Test session management across login-logout-login cycle.
     * Validates: Requirements 4.1, 4.2, 4.3, 4.4
     */
    @Test
    public void testSessionManagementAcrossMultipleLogins() {
        // First login
        AuthenticationResult firstLogin = authService.login("alice", "Password123");
        assertTrue(firstLogin.isSuccessful());
        String firstSessionId = firstLogin.getSessionId();
        
        // Logout
        authService.logout(firstSessionId);
        assertFalse(authService.isAuthenticated(firstSessionId));
        
        // Second login - should get new session ID
        AuthenticationResult secondLogin = authService.login("alice", "Password123");
        assertTrue(secondLogin.isSuccessful());
        String secondSessionId = secondLogin.getSessionId();
        
        // Verify new session ID is different
        assertNotEquals(firstSessionId, secondSessionId, 
            "New login should create new session ID");
        
        // Verify old session is still invalid
        assertFalse(authService.isAuthenticated(firstSessionId));
        
        // Verify new session is valid
        assertTrue(authService.isAuthenticated(secondSessionId));
        assertEquals("alice", authService.getCurrentUser(secondSessionId));
    }
}
