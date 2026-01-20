package com.mycompany.app.auth.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * Property-based tests for secure identifier generation.
 * 
 * Feature: user-authentication
 * Property 12: Secure Identifier Generation
 * 
 * **Validates: Requirements 5.3, 5.4**
 * 
 * For any generated identifier (session ID, reset token), it should be 
 * cryptographically secure and unpredictable.
 */
public class SecureIdentifierPropertyTest {
    
    private SecureIdentifierGenerator generator;
    
    @BeforeEach
    public void setUp() {
        generator = new SecureIdentifierGenerator();
    }
    
    /**
     * Property: For any generated session ID, it should be unique.
     * This ensures unpredictability and prevents collisions.
     */
    @Test
    public void property_SessionIdsAreUnique() {
        Set<String> sessionIds = new HashSet<>();
        
        // Generate 50 session IDs and verify uniqueness
        IntStream.range(0, 50).forEach(i -> {
            String sessionId = generator.generateSessionId();
            assertTrue(sessionIds.add(sessionId),
                "Session ID should be unique, but found duplicate: " + sessionId);
        });
        
        // Verify we generated exactly 50 unique IDs
        assertEquals(50, sessionIds.size());
    }
    
    /**
     * Property: For any generated session ID, it should be non-empty and contain sufficient entropy.
     * This ensures the identifier is cryptographically secure.
     */
    @Test
    public void property_SessionIdsHaveSufficientLength() {
        // Generate 20 session IDs and verify length
        IntStream.range(0, 20).forEach(i -> {
            String sessionId = generator.generateSessionId();
            
            assertNotNull(sessionId, "Session ID should not be null");
            assertFalse(sessionId.isEmpty(), "Session ID should not be empty");
            
            // Session ID should have sufficient length (UUID + random part)
            // UUID is 36 chars, hyphen is 1, random part is ~22 chars = ~59 chars minimum
            assertTrue(sessionId.length() >= 50,
                "Session ID should have sufficient length for security: " + sessionId);
        });
    }
    
    /**
     * Property: For any generated password reset token, it should be unique.
     * This ensures unpredictability and prevents token reuse attacks.
     */
    @Test
    public void property_PasswordResetTokensAreUnique() {
        Set<String> tokens = new HashSet<>();
        
        // Generate 50 tokens and verify uniqueness
        IntStream.range(0, 50).forEach(i -> {
            String token = generator.generatePasswordResetToken();
            assertTrue(tokens.add(token),
                "Password reset token should be unique, but found duplicate: " + token);
        });
        
        // Verify we generated exactly 50 unique tokens
        assertEquals(50, tokens.size());
    }
    
    /**
     * Property: For any generated password reset token, it should be non-empty and have sufficient entropy.
     * This ensures the token is cryptographically secure.
     */
    @Test
    public void property_PasswordResetTokensHaveSufficientLength() {
        // Generate 20 tokens and verify length
        IntStream.range(0, 20).forEach(i -> {
            String token = generator.generatePasswordResetToken();
            
            assertNotNull(token, "Token should not be null");
            assertFalse(token.isEmpty(), "Token should not be empty");
            
            // Default token is 32 bytes, Base64 encoded = ~43 characters
            assertTrue(token.length() >= 40,
                "Token should have sufficient length for security: " + token);
        });
    }
    
    /**
     * Property: For any custom token length, the generated token should be unique.
     * This ensures the generator works correctly with different lengths.
     */
    @Test
    public void property_CustomLengthTokensAreUnique() {
        Set<String> tokens = new HashSet<>();
        
        // Test with different token lengths
        int[] lengths = {16, 24, 32, 48, 64};
        
        for (int length : lengths) {
            tokens.clear();
            
            // Generate 20 tokens for each length
            IntStream.range(0, 20).forEach(i -> {
                String token = generator.generatePasswordResetToken(length);
                assertTrue(tokens.add(token),
                    "Token of length " + length + " should be unique, but found duplicate: " + token);
            });
            
            assertEquals(20, tokens.size(), 
                "Should generate 20 unique tokens for length " + length);
        }
    }
    
    /**
     * Property: For any generated identifier, it should not be predictable from previous identifiers.
     * This tests that consecutive identifiers don't have obvious patterns.
     */
    @Test
    public void property_IdentifiersAreUnpredictable() {
        // Generate 20 pairs of consecutive identifiers
        IntStream.range(0, 20).forEach(i -> {
            String id1 = generator.generateSessionId();
            String id2 = generator.generateSessionId();
            
            // Identifiers should be completely different
            assertNotEquals(id1, id2, "Consecutive identifiers should be different");
            
            // Check that they don't share obvious patterns (e.g., same prefix)
            // Extract the UUID part (first 36 characters)
            String uuid1 = id1.substring(0, Math.min(36, id1.length()));
            String uuid2 = id2.substring(0, Math.min(36, id2.length()));
            
            assertNotEquals(uuid1, uuid2, 
                "Consecutive identifiers should have different UUID parts");
        });
    }
    
    /**
     * Property: For any generated token, it should use URL-safe characters.
     * This ensures tokens can be safely used in URLs and web contexts.
     */
    @Test
    public void property_TokensAreUrlSafe() {
        // Generate 20 tokens and verify they're URL-safe
        IntStream.range(0, 20).forEach(i -> {
            String token = generator.generatePasswordResetToken();
            
            // URL-safe Base64 uses: A-Z, a-z, 0-9, -, _
            // Should not contain: +, /, =
            assertFalse(token.contains("+"), 
                "Token should not contain '+' character: " + token);
            assertFalse(token.contains("/"), 
                "Token should not contain '/' character: " + token);
            assertFalse(token.contains("="), 
                "Token should not contain '=' padding: " + token);
            
            // Should only contain valid URL-safe characters
            assertTrue(token.matches("[A-Za-z0-9_-]+"),
                "Token should only contain URL-safe characters: " + token);
        });
    }
}
