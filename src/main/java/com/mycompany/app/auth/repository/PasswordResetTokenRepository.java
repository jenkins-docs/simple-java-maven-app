package com.mycompany.app.auth.repository;

import com.mycompany.app.auth.model.PasswordResetToken;

import java.util.Optional;

/**
 * Repository interface for password reset token operations.
 * 
 * This interface defines the contract for storing, retrieving, and managing
 * password reset tokens. Implementations should ensure thread-safety and
 * proper handling of token expiration.
 */
public interface PasswordResetTokenRepository {
    
    /**
     * Stores a password reset token.
     * If a token already exists for the username, it should be replaced.
     * 
     * @param token the password reset token to store
     * @throws IllegalArgumentException if token is null
     */
    void save(PasswordResetToken token);
    
    /**
     * Retrieves a password reset token by its token string.
     * 
     * @param token the token string to look up
     * @return Optional containing the PasswordResetToken if found, empty otherwise
     * @throws IllegalArgumentException if token is null or empty
     */
    Optional<PasswordResetToken> findByToken(String token);
    
    /**
     * Invalidates (removes) a password reset token.
     * This should be called after a token is used or when it needs to be revoked.
     * 
     * @param token the token string to invalidate
     * @throws IllegalArgumentException if token is null or empty
     */
    void invalidateToken(String token);
    
    /**
     * Removes all expired tokens from storage.
     * This method should be called periodically to clean up old tokens.
     */
    void cleanupExpiredTokens();
}
