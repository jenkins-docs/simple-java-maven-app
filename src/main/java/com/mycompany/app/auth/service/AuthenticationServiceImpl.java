package com.mycompany.app.auth.service;

import com.mycompany.app.auth.model.PasswordResetToken;
import com.mycompany.app.auth.model.Session;
import com.mycompany.app.auth.model.User;
import com.mycompany.app.auth.repository.PasswordResetTokenRepository;
import com.mycompany.app.auth.repository.SessionRepository;
import com.mycompany.app.auth.repository.UserRepository;
import com.mycompany.app.auth.result.AuthenticationResult;
import com.mycompany.app.auth.result.PasswordResetResult;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Implementation of the AuthenticationService interface.
 * 
 * This class wires together all authentication components including:
 * - User repository for user data access
 * - Session repository for session management
 * - Password reset token repository for password reset operations
 * - Password hasher for secure password operations
 * - Secure identifier generator for session IDs and tokens
 * 
 * All methods implement comprehensive input validation and security checks
 * following the security requirements specified in the design document.
 */
public class AuthenticationServiceImpl implements AuthenticationService {
    
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordHasher passwordHasher;
    private final SecureIdentifierGenerator identifierGenerator;
    
    // Password validation pattern: at least 8 characters, one uppercase, one lowercase, one digit
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$"
    );
    
    private static final String GENERIC_AUTH_ERROR = "Invalid credentials";
    private static final String GENERIC_RESET_ERROR = "Unable to process password reset request";
    
    /**
     * Creates a new AuthenticationServiceImpl with all required dependencies.
     * 
     * @param userRepository the repository for user data operations
     * @param sessionRepository the repository for session management
     * @param tokenRepository the repository for password reset tokens
     * @param passwordHasher the password hasher for secure password operations
     * @param identifierGenerator the generator for secure identifiers
     * @throws IllegalArgumentException if any parameter is null
     */
    public AuthenticationServiceImpl(
            UserRepository userRepository,
            SessionRepository sessionRepository,
            PasswordResetTokenRepository tokenRepository,
            PasswordHasher passwordHasher,
            SecureIdentifierGenerator identifierGenerator) {
        
        if (userRepository == null) {
            throw new IllegalArgumentException("UserRepository cannot be null");
        }
        if (sessionRepository == null) {
            throw new IllegalArgumentException("SessionRepository cannot be null");
        }
        if (tokenRepository == null) {
            throw new IllegalArgumentException("PasswordResetTokenRepository cannot be null");
        }
        if (passwordHasher == null) {
            throw new IllegalArgumentException("PasswordHasher cannot be null");
        }
        if (identifierGenerator == null) {
            throw new IllegalArgumentException("SecureIdentifierGenerator cannot be null");
        }
        
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.tokenRepository = tokenRepository;
        this.passwordHasher = passwordHasher;
        this.identifierGenerator = identifierGenerator;
    }
    
    @Override
    public AuthenticationResult login(String username, String password) {
        // Input validation
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        
        // Clean up expired sessions before processing login
        sessionRepository.cleanupExpiredSessions();
        
        // Find user by username
        Optional<User> userOpt = userRepository.findByUsername(username.trim());
        
        // If user doesn't exist, return generic error (don't reveal user existence)
        if (!userOpt.isPresent()) {
            return AuthenticationResult.failure(GENERIC_AUTH_ERROR);
        }
        
        User user = userOpt.get();
        
        // Verify password
        boolean passwordValid = passwordHasher.verifyPassword(password, user.getHashedPassword());
        
        if (!passwordValid) {
            return AuthenticationResult.failure(GENERIC_AUTH_ERROR);
        }
        
        // Password is valid - create session
        String sessionId = identifierGenerator.generateSessionId();
        Session session = new Session(sessionId, user.getUsername());
        sessionRepository.createSession(session);
        
        // Update last login timestamp
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);
        
        return AuthenticationResult.success(sessionId);
    }
    
    @Override
    public void logout(String sessionId) {
        // Input validation
        if (sessionId == null || sessionId.trim().isEmpty()) {
            throw new IllegalArgumentException("Session ID cannot be null or empty");
        }
        
        // Invalidate the session (handles non-existent sessions gracefully)
        sessionRepository.invalidateSession(sessionId.trim());
        
        // Clean up expired sessions as part of logout
        sessionRepository.cleanupExpiredSessions();
    }
    
    @Override
    public boolean isAuthenticated(String sessionId) {
        // Input validation
        if (sessionId == null || sessionId.trim().isEmpty()) {
            throw new IllegalArgumentException("Session ID cannot be null or empty");
        }
        
        // Check if session is valid (exists and not expired)
        return sessionRepository.isValid(sessionId.trim());
    }
    
    @Override
    public String getCurrentUser(String sessionId) {
        // Input validation
        if (sessionId == null || sessionId.trim().isEmpty()) {
            throw new IllegalArgumentException("Session ID cannot be null or empty");
        }
        
        // Get user from session if valid
        Optional<String> userOpt = sessionRepository.getUser(sessionId.trim());
        return userOpt.orElse(null);
    }
    
    @Override
    public PasswordResetResult initiatePasswordReset(String username) {
        // Input validation
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        
        // Clean up expired tokens before processing
        tokenRepository.cleanupExpiredTokens();
        
        // Find user by username
        Optional<User> userOpt = userRepository.findByUsername(username.trim());
        
        // If user doesn't exist, return generic error (don't reveal user existence)
        if (!userOpt.isPresent()) {
            return PasswordResetResult.failure(GENERIC_RESET_ERROR);
        }
        
        // Generate secure reset token
        String token = identifierGenerator.generatePasswordResetToken();
        PasswordResetToken resetToken = new PasswordResetToken(token, username.trim());
        
        // Store the token
        tokenRepository.save(resetToken);
        
        return PasswordResetResult.success(token);
    }
    
    @Override
    public boolean completePasswordReset(String token, String newPassword) {
        // Input validation
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Token cannot be null or empty");
        }
        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("New password cannot be null or empty");
        }
        
        // Validate password meets security requirements
        if (!isPasswordValid(newPassword)) {
            return false;
        }
        
        // Find token
        Optional<PasswordResetToken> tokenOpt = tokenRepository.findByToken(token.trim());
        
        if (!tokenOpt.isPresent()) {
            return false;
        }
        
        PasswordResetToken resetToken = tokenOpt.get();
        
        // Check if token is expired
        if (resetToken.isExpired()) {
            // Clean up expired token
            tokenRepository.invalidateToken(token.trim());
            return false;
        }
        
        // Token is valid - update password
        String hashedPassword = passwordHasher.hashPassword(newPassword);
        userRepository.updatePassword(resetToken.getUsername(), hashedPassword);
        
        // Invalidate token after use (single-use tokens)
        tokenRepository.invalidateToken(token.trim());
        
        return true;
    }
    
    /**
     * Validates that a password meets security requirements.
     * 
     * Requirements:
     * - At least 8 characters long
     * - Contains at least one uppercase letter
     * - Contains at least one lowercase letter
     * - Contains at least one digit
     * 
     * @param password the password to validate
     * @return true if password meets requirements, false otherwise
     */
    private boolean isPasswordValid(String password) {
        if (password == null) {
            return false;
        }
        return PASSWORD_PATTERN.matcher(password).matches();
    }
}
