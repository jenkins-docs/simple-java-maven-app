package com.mycompany.app.auth.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Session entity for tracking authenticated users.
 * Represents a temporary authenticated state that persists user login status.
 */
public class Session {
    private String sessionId;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    /**
     * Creates a new Session with the specified parameters.
     *
     * @param sessionId the unique session identifier
     * @param username the username associated with this session
     * @param createdAt the timestamp when the session was created
     * @param expiresAt the timestamp when the session expires
     */
    public Session(String sessionId, String username, LocalDateTime createdAt, LocalDateTime expiresAt) {
        this.sessionId = sessionId;
        this.username = username;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    /**
     * Creates a new Session with the specified session ID and username.
     * Sets createdAt to current time and expiresAt to 30 minutes from now.
     *
     * @param sessionId the unique session identifier
     * @param username the username associated with this session
     */
    public Session(String sessionId, String username) {
        this(sessionId, username, LocalDateTime.now(), LocalDateTime.now().plusMinutes(30));
    }

    /**
     * Checks if this session has expired.
     *
     * @return true if the session has expired, false otherwise
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(sessionId, session.sessionId) &&
               Objects.equals(username, session.username) &&
               Objects.equals(createdAt, session.createdAt) &&
               Objects.equals(expiresAt, session.expiresAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, username, createdAt, expiresAt);
    }

    @Override
    public String toString() {
        return "Session{" +
               "sessionId='" + sessionId + '\'' +
               ", username='" + username + '\'' +
               ", createdAt=" + createdAt +
               ", expiresAt=" + expiresAt +
               ", expired=" + isExpired() +
               '}';
    }
}
