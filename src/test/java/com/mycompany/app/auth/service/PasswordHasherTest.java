package com.mycompany.app.auth.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for PasswordHasher.
 * Tests password hashing and verification functionality.
 */
public class PasswordHasherTest {
    
    private PasswordHasher hasher;
    
    @BeforeEach
    public void setUp() {
        // Use minimum work factor (4) for faster tests
        hasher = new PasswordHasher(4);
    }
    
    @Test
    public void testHashPassword_ValidPassword_ReturnsHash() {
        String password = "mySecurePassword123";
        String hash = hasher.hashPassword(password);
        
        assertNotNull(hash);
        assertFalse(hash.isEmpty());
        assertNotEquals(password, hash);
        assertTrue(hash.startsWith("$2a$")); // BCrypt hash format
    }
    
    @Test
    public void testHashPassword_SamePasswordTwice_ReturnsDifferentHashes() {
        String password = "mySecurePassword123";
        String hash1 = hasher.hashPassword(password);
        String hash2 = hasher.hashPassword(password);
        
        assertNotEquals(hash1, hash2); // Different salts
    }
    
    @Test
    public void testHashPassword_NullPassword_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            hasher.hashPassword(null);
        });
    }
    
    @Test
    public void testHashPassword_EmptyPassword_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            hasher.hashPassword("");
        });
    }
    
    @Test
    public void testVerifyPassword_CorrectPassword_ReturnsTrue() {
        String password = "mySecurePassword123";
        String hash = hasher.hashPassword(password);
        
        assertTrue(hasher.verifyPassword(password, hash));
    }
    
    @Test
    public void testVerifyPassword_IncorrectPassword_ReturnsFalse() {
        String password = "mySecurePassword123";
        String wrongPassword = "wrongPassword";
        String hash = hasher.hashPassword(password);
        
        assertFalse(hasher.verifyPassword(wrongPassword, hash));
    }
    
    @Test
    public void testVerifyPassword_NullPassword_ThrowsException() {
        String hash = hasher.hashPassword("password");
        
        assertThrows(IllegalArgumentException.class, () -> {
            hasher.verifyPassword(null, hash);
        });
    }
    
    @Test
    public void testVerifyPassword_NullHash_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            hasher.verifyPassword("password", null);
        });
    }
    
    @Test
    public void testVerifyPassword_InvalidHashFormat_ReturnsFalse() {
        assertFalse(hasher.verifyPassword("password", "invalid-hash"));
    }
    
    @Test
    public void testCustomWorkFactor_ValidFactor_CreatesHasher() {
        PasswordHasher customHasher = new PasswordHasher(10);
        assertEquals(10, customHasher.getWorkFactor());
        
        String hash = customHasher.hashPassword("password");
        assertNotNull(hash);
        assertTrue(customHasher.verifyPassword("password", hash));
    }
    
    @Test
    public void testCustomWorkFactor_TooLow_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PasswordHasher(3);
        });
    }
    
    @Test
    public void testCustomWorkFactor_TooHigh_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PasswordHasher(32);
        });
    }
}
