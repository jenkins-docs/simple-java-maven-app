package com.mycompany.app.auth.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * Property-based tests for password security.
 * 
 * Feature: user-authentication
 * Property 3: Password Security Invariant
 * 
 * **Validates: Requirements 1.5, 5.1, 5.2**
 * 
 * For any password stored in the system, it should never be stored in plaintext 
 * format and should always use cryptographically secure hashing (BCrypt).
 */
public class PasswordSecurityPropertyTest {
    
    private PasswordHasher hasher;
    private Random random;
    
    @BeforeEach
    public void setUp() {
        // Use minimum work factor (4) for faster tests
        hasher = new PasswordHasher(4);
        random = new Random();
    }
    
    /**
     * Property: For any password, the hash should never equal the plaintext password.
     * This ensures passwords are never stored in plaintext.
     */
    @Test
    public void property_HashNeverEqualsPlaintext() {
        // Run 20 iterations with random passwords
        IntStream.range(0, 20).forEach(i -> {
            String password = generateRandomPassword();
            String hash = hasher.hashPassword(password);
            
            // Hash should never equal the plaintext password
            assertNotEquals(password, hash, 
                "Hash should never equal plaintext password for: " + password);
        });
    }
    
    /**
     * Property: For any password, the hash should always use BCrypt format.
     * BCrypt hashes start with "$2a$", "$2b$", or "$2y$" followed by the work factor.
     */
    @Test
    public void property_HashAlwaysUsesBCryptFormat() {
        // Run 20 iterations with random passwords
        IntStream.range(0, 20).forEach(i -> {
            String password = generateRandomPassword();
            String hash = hasher.hashPassword(password);
            
            // Hash should use BCrypt format
            assertTrue(hash.startsWith("$2a$") || hash.startsWith("$2b$") || hash.startsWith("$2y$"),
                "Hash should use BCrypt format for password: " + password + ", got: " + hash);
            
            // Hash should have proper BCrypt structure (60 characters)
            assertEquals(60, hash.length(),
                "BCrypt hash should be 60 characters long for password: " + password);
        });
    }
    
    /**
     * Property: For any password hashed twice, the hashes should be different (due to salt).
     * This ensures each hash uses a unique salt.
     */
    @Test
    public void property_SamePasswordProducesDifferentHashes() {
        // Run 20 iterations with random passwords
        IntStream.range(0, 20).forEach(i -> {
            String password = generateRandomPassword();
            String hash1 = hasher.hashPassword(password);
            String hash2 = hasher.hashPassword(password);
            
            // Same password should produce different hashes due to different salts
            assertNotEquals(hash1, hash2,
                "Same password should produce different hashes due to salt: " + password);
        });
    }
    
    /**
     * Property: For any password and its hash, verification should always succeed.
     * This ensures the hashing and verification are consistent.
     */
    @Test
    public void property_VerificationAlwaysSucceedsForCorrectPassword() {
        // Run 20 iterations with random passwords
        IntStream.range(0, 20).forEach(i -> {
            String password = generateRandomPassword();
            String hash = hasher.hashPassword(password);
            
            // Verification should always succeed for the correct password
            assertTrue(hasher.verifyPassword(password, hash),
                "Verification should succeed for correct password: " + password);
        });
    }
    
    /**
     * Property: For any password and a different password, verification should fail.
     * This ensures the hashing provides proper security.
     */
    @Test
    public void property_VerificationFailsForIncorrectPassword() {
        // Run 20 iterations with random passwords
        IntStream.range(0, 20).forEach(i -> {
            String password = generateRandomPassword();
            String wrongPassword = generateRandomPassword();
            
            // Skip if we accidentally generated the same password
            if (password.equals(wrongPassword)) {
                return;
            }
            
            String hash = hasher.hashPassword(password);
            
            // Verification should fail for incorrect password
            assertFalse(hasher.verifyPassword(wrongPassword, hash),
                "Verification should fail for incorrect password. Original: " + password + 
                ", Wrong: " + wrongPassword);
        });
    }
    
    /**
     * Property: For any password, the hash should not contain the plaintext password.
     * This ensures no information leakage in the hash.
     */
    @Test
    public void property_HashDoesNotContainPlaintext() {
        // Run 20 iterations with random passwords
        IntStream.range(0, 20).forEach(i -> {
            String password = generateRandomPassword();
            String hash = hasher.hashPassword(password);
            
            // Hash should not contain the plaintext password
            assertFalse(hash.contains(password),
                "Hash should not contain plaintext password: " + password);
        });
    }
    
    /**
     * Generates a random password for testing.
     * Includes various character types and lengths.
     */
    private String generateRandomPassword() {
        int length = 8 + random.nextInt(25); // Length between 8 and 32
        StringBuilder password = new StringBuilder();
        
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+-=[]{}|;:,.<>?";
        
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        return password.toString();
    }
}
