package com.mycompany.app.auth.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Session data model.
 */
class SessionTest {

    @Test
    void testSessionConstructorWithAllParameters() {
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(30);
        
        Session session = new Session("session123", "testuser", createdAt, expiresAt);
        
        assertEquals("session123", session.getSessionId());
        assertEquals("testuser", session.getUsername());
        assertEquals(createdAt, session.getCreatedAt());
        assertEquals(expiresAt, session.getExpiresAt());
    }

    @Test
    void testSessionConstructorWithIdAndUsername() {
        Session session = new Session("session123", "testuser");
        
        assertEquals("session123", session.getSessionId());
        assertEquals("testuser", session.getUsername());
        assertNotNull(session.getCreatedAt());
        assertNotNull(session.getExpiresAt());
    }

    @Test
    void testSessionIsNotExpiredWhenValid() {
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(30);
        
        Session session = new Session("session123", "testuser", createdAt, expiresAt);
        
        assertFalse(session.isExpired());
    }

    @Test
    void testSessionIsExpiredWhenPastExpiration() {
        LocalDateTime createdAt = LocalDateTime.now().minusHours(2);
        LocalDateTime expiresAt = LocalDateTime.now().minusHours(1);
        
        Session session = new Session("session123", "testuser", createdAt, expiresAt);
        
        assertTrue(session.isExpired());
    }

    @Test
    void testSessionSetters() {
        Session session = new Session("session123", "testuser");
        LocalDateTime newExpiration = LocalDateTime.now().plusHours(1);
        
        session.setSessionId("newsession456");
        session.setUsername("newuser");
        session.setExpiresAt(newExpiration);
        
        assertEquals("newsession456", session.getSessionId());
        assertEquals("newuser", session.getUsername());
        assertEquals(newExpiration, session.getExpiresAt());
    }

    @Test
    void testSessionEquals() {
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(30);
        
        Session session1 = new Session("session123", "testuser", createdAt, expiresAt);
        Session session2 = new Session("session123", "testuser", createdAt, expiresAt);
        Session session3 = new Session("different", "testuser", createdAt, expiresAt);
        
        assertEquals(session1, session2);
        assertNotEquals(session1, session3);
        assertNotEquals(session1, null);
        assertEquals(session1, session1);
    }

    @Test
    void testSessionHashCode() {
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(30);
        
        Session session1 = new Session("session123", "testuser", createdAt, expiresAt);
        Session session2 = new Session("session123", "testuser", createdAt, expiresAt);
        
        assertEquals(session1.hashCode(), session2.hashCode());
    }

    @Test
    void testSessionToString() {
        Session session = new Session("session123", "testuser");
        String toString = session.toString();
        
        assertTrue(toString.contains("session123"));
        assertTrue(toString.contains("testuser"));
        assertTrue(toString.contains("expired="));
    }
}
