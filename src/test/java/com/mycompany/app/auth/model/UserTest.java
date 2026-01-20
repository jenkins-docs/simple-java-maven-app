package com.mycompany.app.auth.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the User data model.
 */
class UserTest {

    @Test
    void testUserConstructorWithAllParameters() {
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime lastLoginAt = LocalDateTime.now();
        
        User user = new User("testuser", "hashedpass123", createdAt, lastLoginAt);
        
        assertEquals("testuser", user.getUsername());
        assertEquals("hashedpass123", user.getHashedPassword());
        assertEquals(createdAt, user.getCreatedAt());
        assertEquals(lastLoginAt, user.getLastLoginAt());
    }

    @Test
    void testUserConstructorWithUsernameAndPassword() {
        User user = new User("testuser", "hashedpass123");
        
        assertEquals("testuser", user.getUsername());
        assertEquals("hashedpass123", user.getHashedPassword());
        assertNotNull(user.getCreatedAt());
        assertNull(user.getLastLoginAt());
    }

    @Test
    void testUserSetters() {
        User user = new User("testuser", "hashedpass123");
        LocalDateTime newLoginTime = LocalDateTime.now();
        
        user.setUsername("newuser");
        user.setHashedPassword("newhash456");
        user.setLastLoginAt(newLoginTime);
        
        assertEquals("newuser", user.getUsername());
        assertEquals("newhash456", user.getHashedPassword());
        assertEquals(newLoginTime, user.getLastLoginAt());
    }

    @Test
    void testUserEquals() {
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime lastLoginAt = LocalDateTime.now();
        
        User user1 = new User("testuser", "hashedpass123", createdAt, lastLoginAt);
        User user2 = new User("testuser", "hashedpass123", createdAt, lastLoginAt);
        User user3 = new User("different", "hashedpass123", createdAt, lastLoginAt);
        
        assertEquals(user1, user2);
        assertNotEquals(user1, user3);
        assertNotEquals(user1, null);
        assertEquals(user1, user1);
    }

    @Test
    void testUserHashCode() {
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime lastLoginAt = LocalDateTime.now();
        
        User user1 = new User("testuser", "hashedpass123", createdAt, lastLoginAt);
        User user2 = new User("testuser", "hashedpass123", createdAt, lastLoginAt);
        
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void testUserToString() {
        User user = new User("testuser", "hashedpass123");
        String toString = user.toString();
        
        assertTrue(toString.contains("testuser"));
        assertTrue(toString.contains("[PROTECTED]"));
        assertFalse(toString.contains("hashedpass123"));
    }
}
