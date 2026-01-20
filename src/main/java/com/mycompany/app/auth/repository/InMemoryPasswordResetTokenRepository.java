package com.mycompany.app.auth.repository;

import com.mycompany.app.auth.model.PasswordResetToken;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory implementation of PasswordResetTokenRepository.
 * 
 * This implementation uses ConcurrentHashMap for thread-safe operations
 * and is suitable for single-instance applications or development environments.
 * 
 * Tokens are stored by their token string as the key, and the repository
 * maintains a separate index by username to ensure only one active token
 * per user at a time.
 */
public class InMemoryPasswordResetTokenRepository implements PasswordResetTokenRepository {
    
    private final ConcurrentHashMap<String, PasswordResetToken> tokenStore;
    private final ConcurrentHashMap<String, String> usernameToTokenIndex;
    
    /**
     * Creates a new InMemoryPasswordResetTokenRepository with empty storage.
     */
    public InMemoryPasswordResetTokenRepository() {
        this.tokenStore = new ConcurrentHashMap<>();
        this.usernameToTokenIndex = new ConcurrentHashMap<>();
    }
    
    /**
     * Stores a password reset token.
     * If a token already exists for the username, the old token is invalidated
     * and replaced with the new one.
     * 
     * @param token the password reset token to store
     * @throws IllegalArgumentException if token is null
     */
    @Override
    public void save(PasswordResetToken token) {
        if (token == null) {
            throw new IllegalArgumentException("Token cannot be null");
        }
        
        String username = token.getUsername();
        String tokenString = token.getToken();
        
        // If there's an existing token for this username, remove it
        String existingToken = usernameToTokenIndex.get(username);
        if (existingToken != null) {
            tokenStore.remove(existingToken);
        }
        
        // Store the new token
        tokenStore.put(tokenString, token);
        usernameToTokenIndex.put(username, tokenString);
    }
    
    /**
     * Retrieves a password reset token by its token string.
     * 
     * @param token the token string to look up
     * @return Optional containing the PasswordResetToken if found, empty otherwise
     * @throws IllegalArgumentException if token is null or empty
     */
    @Override
    public Optional<PasswordResetToken> findByToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Token cannot be null or empty");
        }
        
        return Optional.ofNullable(tokenStore.get(token));
    }
    
    /**
     * Invalidates (removes) a password reset token.
     * This should be called after a token is used or when it needs to be revoked.
     * 
     * @param token the token string to invalidate
     * @throws IllegalArgumentException if token is null or empty
     */
    @Override
    public void invalidateToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Token cannot be null or empty");
        }
        
        PasswordResetToken resetToken = tokenStore.remove(token);
        if (resetToken != null) {
            usernameToTokenIndex.remove(resetToken.getUsername());
        }
    }
    
    /**
     * Removes all expired tokens from storage.
     * This method should be called periodically to clean up old tokens.
     */
    @Override
    public void cleanupExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        
        tokenStore.entrySet().removeIf(entry -> {
            PasswordResetToken token = entry.getValue();
            boolean expired = now.isAfter(token.getExpiresAt());
            
            if (expired) {
                // Also remove from username index
                usernameToTokenIndex.remove(token.getUsername());
            }
            
            return expired;
        });
    }
}
