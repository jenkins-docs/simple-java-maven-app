package com.mycompany.app.auth.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the PasswordResetToken data model.
 */
class PasswordResetTokenTest {

    @Test
    void testPasswordResetTokenConstructorWithAllParameters() {
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiresAt = LocalDateTime.now().plusHours(1);
        
        PasswordResetToken token = new PasswordResetToken("token123", "testuser", createdAt, expiresAt);
        
        assertEquals("token123", token.getToken());
        assertEquals("testuser", token.getUsername());
        assertEquals(createdAt, token.getCreatedAt());
        assertEquals(expiresAt, token.getExpiresAt());
    }

    @Test
    void testPasswordResetTokenConstructorWithTokenAndUsername() {
        PasswordResetToken token = new PasswordResetToken("token123", "testuser");
        
        assertEquals("token123", token.getToken());
        assertEquals("testuser", token.getUsername());
        assertNotNull(token.getCreatedAt());
        assertNotNull(token.getExpiresAt());
    }

    @Test
    void testPasswordResetTokenIsNotExpiredWhenValid() {
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiresAt = LocalDateTime.now().plusHours(1);
        
        PasswordResetToken token = new PasswordResetToken("token123", "testuser", createdAt, expiresAt);
        
        assertFalse(token.isExpired());
    }

    @Test
    void testPasswordResetTokenIsExpiredWhenPastExpiration() {
        LocalDateTime createdAt = LocalDateTime.now().minusHours(2);
        LocalDateTime expiresAt = LocalDateTime.now().minusHours(1);
        
        PasswordResetToken token = new PasswordResetToken("token123", "testuser", createdAt, expiresAt);
        
        assertTrue(token.isExpired());
    }

    @Test
    void testPasswordResetTokenSetters() {
        PasswordResetToken token = new PasswordResetToken("token123", "testuser");
        LocalDateTime newExpiration = LocalDateTime.now().plusHours(2);
        
        token.setToken("newtoken456");
        token.setUsername("newuser");
        token.setExpiresAt(newExpiration);
        
        assertEquals("newtoken456", token.getToken());
        assertEquals("newuser", token.getUsername());
        assertEquals(newExpiration, token.getExpiresAt());
    }

    @Test
    void testPasswordResetTokenEquals() {
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiresAt = LocalDateTime.now().plusHours(1);
        
        PasswordResetToken token1 = new PasswordResetToken("token123", "testuser", createdAt, expiresAt);
        PasswordResetToken token2 = new PasswordResetToken("token123", "testuser", createdAt, expiresAt);
        PasswordResetToken token3 = new PasswordResetToken("different", "testuser", createdAt, expiresAt);
        
        assertEquals(token1, token2);
        assertNotEquals(token1, token3);
        assertNotEquals(token1, null);
        assertEquals(token1, token1);
    }

    @Test
    void testPasswordResetTokenHashCode() {
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiresAt = LocalDateTime.now().plusHours(1);
        
        PasswordResetToken token1 = new PasswordResetToken("token123", "testuser", createdAt, expiresAt);
        PasswordResetToken token2 = new PasswordResetToken("token123", "testuser", createdAt, expiresAt);
        
        assertEquals(token1.hashCode(), token2.hashCode());
    }

    @Test
    void testPasswordResetTokenToString() {
        PasswordResetToken token = new PasswordResetToken("token123", "testuser");
        String toString = token.toString();
        
        assertTrue(toString.contains("token123"));
        assertTrue(toString.contains("testuser"));
        assertTrue(toString.contains("expired="));
    }
}
