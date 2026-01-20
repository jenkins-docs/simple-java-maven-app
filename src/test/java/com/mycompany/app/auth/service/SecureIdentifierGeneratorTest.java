package com.mycompany.app.auth.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Unit tests for SecureIdentifierGenerator.
 * Tests secure identifier generation functionality.
 */
public class SecureIdentifierGeneratorTest {
    
    private SecureIdentifierGenerator generator;
    
    @BeforeEach
    public void setUp() {
        generator = new SecureIdentifierGenerator();
    }
    
    @Test
    public void testGenerateSessionId_ReturnsNonNull() {
        String sessionId = generator.generateSessionId();
        assertNotNull(sessionId);
        assertFalse(sessionId.isEmpty());
    }
    
    @Test
    public void testGenerateSessionId_GeneratesUniqueIds() {
        Set<String> sessionIds = new HashSet<>();
        
        // Generate 100 session IDs and ensure they're all unique
        for (int i = 0; i < 100; i++) {
            String sessionId = generator.generateSessionId();
            assertTrue(sessionIds.add(sessionId), 
                "Session ID should be unique: " + sessionId);
        }
    }
    
    @Test
    public void testGenerateSessionId_ContainsHyphen() {
        String sessionId = generator.generateSessionId();
        assertTrue(sessionId.contains("-"), 
            "Session ID should contain hyphen separator");
    }
    
    @Test
    public void testGeneratePasswordResetToken_ReturnsNonNull() {
        String token = generator.generatePasswordResetToken();
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }
    
    @Test
    public void testGeneratePasswordResetToken_GeneratesUniqueTokens() {
        Set<String> tokens = new HashSet<>();
        
        // Generate 100 tokens and ensure they're all unique
        for (int i = 0; i < 100; i++) {
            String token = generator.generatePasswordResetToken();
            assertTrue(tokens.add(token), 
                "Token should be unique: " + token);
        }
    }
    
    @Test
    public void testGeneratePasswordResetToken_CustomLength_ReturnsCorrectLength() {
        String token = generator.generatePasswordResetToken(16);
        assertNotNull(token);
        assertFalse(token.isEmpty());
        
        // Base64 encoding of 16 bytes should be approximately 22 characters (without padding)
        assertTrue(token.length() >= 20 && token.length() <= 24);
    }
    
    @Test
    public void testGeneratePasswordResetToken_TooShort_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            generator.generatePasswordResetToken(15);
        });
    }
    
    @Test
    public void testGenerateSecureToken_ValidLength_ReturnsToken() {
        String token = generator.generateSecureToken(32);
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }
    
    @Test
    public void testGenerateSecureToken_ZeroLength_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            generator.generateSecureToken(0);
        });
    }
    
    @Test
    public void testGenerateSecureToken_NegativeLength_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            generator.generateSecureToken(-1);
        });
    }
    
    @Test
    public void testConstructor_NullSecureRandom_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new SecureIdentifierGenerator(null);
        });
    }
}
