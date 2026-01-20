package com.mycompany.app.auth.service;

import com.mycompany.app.auth.result.AuthenticationResult;
import com.mycompany.app.auth.result.PasswordResetResult;

/**
 * Primary service interface for all authentication operations.
 * This interface defines the contract for user authentication, session management,
 * and password reset functionality.
 * 
 * All methods in this interface follow security best practices including:
 * - Generic error messages that don't reveal user existence
 * - Secure password hashing and verification
 * - Cryptographically secure token and session ID generation
 * - Proper session lifecycle management
 */
public interface AuthenticationService {
    
    /**
     * Authenticates a user with their credentials and creates a session.
     * 
     * @param username the username to authenticate
     * @param password the plaintext password to verify
     * @return AuthenticationResult containing session ID on success or error message on failure
     * @throws IllegalArgumentException if username or password is null or empty
     */
    AuthenticationResult login(String username, String password);
    
    /**
     * Invalidates an authenticated session and logs out the user.
     * 
     * @param sessionId the session ID to invalidate
     * @throws IllegalArgumentException if sessionId is null or empty
     */
    void logout(String sessionId);
    
    /**
     * Checks if a session is currently valid and authenticated.
     * 
     * @param sessionId the session ID to check
     * @return true if the session is valid and not expired, false otherwise
     * @throws IllegalArgumentException if sessionId is null or empty
     */
    boolean isAuthenticated(String sessionId);
    
    /**
     * Gets the username associated with a valid session.
     * 
     * @param sessionId the session ID to look up
     * @return the username associated with the session, or null if session is invalid
     * @throws IllegalArgumentException if sessionId is null or empty
     */
    String getCurrentUser(String sessionId);
    
    /**
     * Initiates a password reset process by generating a secure reset token.
     * 
     * @param username the username requesting password reset
     * @return PasswordResetResult containing reset token on success or error message on failure
     * @throws IllegalArgumentException if username is null or empty
     */
    PasswordResetResult initiatePasswordReset(String username);
    
    /**
     * Completes a password reset by validating the token and updating the password.
     * 
     * @param token the password reset token
     * @param newPassword the new plaintext password to set
     * @return true if password reset succeeded, false otherwise
     * @throws IllegalArgumentException if token or newPassword is null or empty
     */
    boolean completePasswordReset(String token, String newPassword);
}