package com.mycompany.app.auth.repository;

import com.mycompany.app.auth.model.Session;
import java.util.Optional;

/**
 * Repository interface for session management operations.
 * Provides methods for creating, retrieving, and invalidating user sessions.
 */
public interface SessionRepository {
    
    /**
     * Creates a new session with the specified session ID and username.
     *
     * @param sessionId the unique session identifier
     * @param username the username associated with this session
     */
    void createSession(String sessionId, String username);
    
    /**
     * Creates a new session with the specified Session object.
     *
     * @param session the session to create
     */
    void createSession(Session session);
    
    /**
     * Retrieves the username associated with the given session ID.
     *
     * @param sessionId the session identifier to look up
     * @return an Optional containing the username if the session exists and is valid, empty otherwise
     */
    Optional<String> getUser(String sessionId);
    
    /**
     * Retrieves the session associated with the given session ID.
     *
     * @param sessionId the session identifier to look up
     * @return an Optional containing the Session if it exists, empty otherwise
     */
    Optional<Session> findBySessionId(String sessionId);
    
    /**
     * Invalidates the session with the specified session ID.
     *
     * @param sessionId the session identifier to invalidate
     */
    void invalidateSession(String sessionId);
    
    /**
     * Removes all expired sessions from the repository.
     * This method should be called periodically to clean up stale sessions.
     */
    void cleanupExpiredSessions();
    
    /**
     * Checks if a session with the given session ID exists and is not expired.
     *
     * @param sessionId the session identifier to check
     * @return true if the session exists and is valid, false otherwise
     */
    boolean isValid(String sessionId);
}
