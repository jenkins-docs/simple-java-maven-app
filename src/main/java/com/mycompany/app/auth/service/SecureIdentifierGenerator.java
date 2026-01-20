package com.mycompany.app.auth.service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

/**
 * Utility class for generating cryptographically secure identifiers.
 * 
 * This class provides methods for:
 * - Generating secure session IDs using UUID and SecureRandom
 * - Generating secure password reset tokens
 * - Ensuring all identifiers are unpredictable and cryptographically secure
 * 
 * All generation methods use SecureRandom, which is designed for
 * cryptographic purposes and provides strong randomness.
 */
public class SecureIdentifierGenerator {
    
    private final SecureRandom secureRandom;
    
    /**
     * Default token length in bytes (32 bytes = 256 bits).
     * This provides sufficient entropy for secure tokens.
     */
    private static final int DEFAULT_TOKEN_LENGTH_BYTES = 32;
    
    /**
     * Creates a SecureIdentifierGenerator with a new SecureRandom instance.
     */
    public SecureIdentifierGenerator() {
        this.secureRandom = new SecureRandom();
    }
    
    /**
     * Creates a SecureIdentifierGenerator with a provided SecureRandom instance.
     * This constructor is useful for testing or when you want to control the random source.
     * 
     * @param secureRandom the SecureRandom instance to use
     * @throws IllegalArgumentException if secureRandom is null
     */
    public SecureIdentifierGenerator(SecureRandom secureRandom) {
        if (secureRandom == null) {
            throw new IllegalArgumentException("SecureRandom cannot be null");
        }
        this.secureRandom = secureRandom;
    }
    
    /**
     * Generates a cryptographically secure session ID.
     * 
     * The session ID is generated using a combination of:
     * - UUID (version 4, random) for uniqueness
     * - Additional random bytes for extra entropy
     * 
     * @return a secure session ID string
     */
    public String generateSessionId() {
        // Use UUID for base uniqueness
        UUID uuid = UUID.randomUUID();
        
        // Add additional random bytes for extra security
        byte[] randomBytes = new byte[16];
        secureRandom.nextBytes(randomBytes);
        String randomPart = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        
        // Combine UUID and random part
        return uuid.toString() + "-" + randomPart;
    }
    
    /**
     * Generates a cryptographically secure password reset token.
     * 
     * The token is generated using SecureRandom and encoded in URL-safe Base64.
     * Default length is 32 bytes (256 bits) which provides strong security.
     * 
     * @return a secure password reset token string
     */
    public String generatePasswordResetToken() {
        return generatePasswordResetToken(DEFAULT_TOKEN_LENGTH_BYTES);
    }
    
    /**
     * Generates a cryptographically secure password reset token with custom length.
     * 
     * @param lengthInBytes the length of the token in bytes (minimum 16)
     * @return a secure password reset token string
     * @throws IllegalArgumentException if lengthInBytes is less than 16
     */
    public String generatePasswordResetToken(int lengthInBytes) {
        if (lengthInBytes < 16) {
            throw new IllegalArgumentException("Token length must be at least 16 bytes");
        }
        
        byte[] tokenBytes = new byte[lengthInBytes];
        secureRandom.nextBytes(tokenBytes);
        
        // Use URL-safe Base64 encoding without padding
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }
    
    /**
     * Generates a cryptographically secure random token of specified length.
     * This is a general-purpose method for generating secure tokens.
     * 
     * @param lengthInBytes the length of the token in bytes
     * @return a secure token string
     * @throws IllegalArgumentException if lengthInBytes is less than 1
     */
    public String generateSecureToken(int lengthInBytes) {
        if (lengthInBytes < 1) {
            throw new IllegalArgumentException("Token length must be at least 1 byte");
        }
        
        byte[] tokenBytes = new byte[lengthInBytes];
        secureRandom.nextBytes(tokenBytes);
        
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }
}
