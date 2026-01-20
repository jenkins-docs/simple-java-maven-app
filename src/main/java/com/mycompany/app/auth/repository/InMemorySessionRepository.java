package com.mycompany.app.auth.repository;

import com.mycompany.app.auth.model.Session;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread-safe in-memory implementation of SessionRepository.
 * Uses ConcurrentHashMap to ensure thread safety for concurrent operations.
 */
public class InMemorySessionRepository implements SessionRepository {
    
    private final ConcurrentHashMap<String, Session> sessions;
    
    /**
     * Creates a new InMemorySessionRepository with an empty session store.
     */
    public InMemorySessionRepository() {
        this.sessions = new ConcurrentHashMap<>();
    }
    
    @Override
    public void createSession(String sessionId, String username) {
        if (sessionId == null || sessionId.trim().isEmpty()) {
            throw new IllegalArgumentException("Session ID cannot be null or empty");
        }
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        
        Session session = new Session(sessionId, username);
        sessions.put(sessionId, session);
    }
    
    @Override
    public void createSession(Session session) {
        if (session == null) {
            throw new IllegalArgumentException("Session cannot be null");
        }
        if (session.getSessionId() == null || session.getSessionId().trim().isEmpty()) {
            throw new IllegalArgumentException("Session ID cannot be null or empty");
        }
        
        sessions.put(session.getSessionId(), session);
    }
    
    @Override
    public Optional<String> getUser(String sessionId) {
        if (sessionId == null || sessionId.trim().isEmpty()) {
            return Optional.empty();
        }
        
        Session session = sessions.get(sessionId);
        if (session == null || session.isExpired()) {
            return Optional.empty();
        }
        
        return Optional.of(session.getUsername());
    }
    
    @Override
    public Optional<Session> findBySessionId(String sessionId) {
        if (sessionId == null || sessionId.trim().isEmpty()) {
            return Optional.empty();
        }
        
        return Optional.ofNullable(sessions.get(sessionId));
    }
    
    @Override
    public void invalidateSession(String sessionId) {
        if (sessionId != null && !sessionId.trim().isEmpty()) {
            sessions.remove(sessionId);
        }
    }
    
    @Override
    public void cleanupExpiredSessions() {
        sessions.entrySet().removeIf(entry -> entry.getValue().isExpired());
    }
    
    @Override
    public boolean isValid(String sessionId) {
        if (sessionId == null || sessionId.trim().isEmpty()) {
            return false;
        }
        
        Session session = sessions.get(sessionId);
        return session != null && !session.isExpired();
    }
}
