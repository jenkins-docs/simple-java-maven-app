package com.mycompany.app.auth.service;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility class for secure password hashing and verification using BCrypt.
 * 
 * This class provides methods for:
 * - Hashing passwords with configurable work factor
 * - Verifying passwords without timing attacks
 * - Never storing or logging plaintext passwords
 * 
 * BCrypt is a password hashing function designed to be slow and resistant to
 * brute-force attacks. The work factor determines the computational cost.
 */
public class PasswordHasher {
    
    /**
     * Default BCrypt work factor (log2 rounds).
     * A work factor of 12 means 2^12 = 4096 rounds.
     * Higher values increase security but also increase computation time.
     */
    private static final int DEFAULT_WORK_FACTOR = 12;
    
    private final int workFactor;
    
    /**
     * Creates a PasswordHasher with the default work factor.
     */
    public PasswordHasher() {
        this(DEFAULT_WORK_FACTOR);
    }
    
    /**
     * Creates a PasswordHasher with a custom work factor.
     * 
     * @param workFactor the BCrypt work factor (typically between 10 and 14)
     * @throws IllegalArgumentException if workFactor is less than 4 or greater than 31
     */
    public PasswordHasher(int workFactor) {
        if (workFactor < 4 || workFactor > 31) {
            throw new IllegalArgumentException("Work factor must be between 4 and 31");
        }
        this.workFactor = workFactor;
    }
    
    /**
     * Hashes a plaintext password using BCrypt.
     * 
     * @param plaintext the plaintext password to hash
     * @return the BCrypt hash of the password
     * @throws IllegalArgumentException if plaintext is null or empty
     */
    public String hashPassword(String plaintext) {
        if (plaintext == null || plaintext.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        
        // Generate a salt and hash the password in one operation
        return BCrypt.hashpw(plaintext, BCrypt.gensalt(workFactor));
    }
    
    /**
     * Verifies a plaintext password against a BCrypt hash.
     * 
     * This method is designed to be resistant to timing attacks by always
     * performing the full BCrypt comparison regardless of early mismatches.
     * 
     * @param plaintext the plaintext password to verify
     * @param hashedPassword the BCrypt hash to verify against
     * @return true if the password matches the hash, false otherwise
     * @throws IllegalArgumentException if plaintext or hashedPassword is null or empty
     */
    public boolean verifyPassword(String plaintext, String hashedPassword) {
        if (plaintext == null || plaintext.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (hashedPassword == null || hashedPassword.isEmpty()) {
            throw new IllegalArgumentException("Hashed password cannot be null or empty");
        }
        
        try {
            // BCrypt.checkpw is designed to be resistant to timing attacks
            return BCrypt.checkpw(plaintext, hashedPassword);
        } catch (IllegalArgumentException e) {
            // Invalid hash format - return false rather than throwing
            return false;
        }
    }
    
    /**
     * Gets the configured work factor for this hasher.
     * 
     * @return the BCrypt work factor
     */
    public int getWorkFactor() {
        return workFactor;
    }
}
