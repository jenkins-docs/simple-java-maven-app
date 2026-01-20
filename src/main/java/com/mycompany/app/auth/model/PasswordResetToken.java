package com.mycompany.app.auth.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Password reset token for secure password changes.
 * Represents a temporary, unique identifier used to authorize password changes.
 */
public class PasswordResetToken {
    private String token;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    /**
     * Creates a new PasswordResetToken with the specified parameters.
     *
     * @param token the unique token identifier
     * @param username the username associated with this token
     * @param createdAt the timestamp when the token was created
     * @param expiresAt the timestamp when the token expires
     */
    public PasswordResetToken(String token, String username, LocalDateTime createdAt, LocalDateTime expiresAt) {
        this.token = token;
        this.username = username;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    /**
     * Creates a new PasswordResetToken with the specified token and username.
     * Sets createdAt to current time and expiresAt to 1 hour from now.
     *
     * @param token the unique token identifier
     * @param username the username associated with this token
     */
    public PasswordResetToken(String token, String username) {
        this(token, username, LocalDateTime.now(), LocalDateTime.now().plusHours(1));
    }

    /**
     * Checks if this token has expired.
     *
     * @return true if the token has expired, false otherwise
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
        PasswordResetToken that = (PasswordResetToken) o;
        return Objects.equals(token, that.token) &&
               Objects.equals(username, that.username) &&
               Objects.equals(createdAt, that.createdAt) &&
               Objects.equals(expiresAt, that.expiresAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, username, createdAt, expiresAt);
    }

    @Override
    public String toString() {
        return "PasswordResetToken{" +
               "token='" + token + '\'' +
               ", username='" + username + '\'' +
               ", createdAt=" + createdAt +
               ", expiresAt=" + expiresAt +
               ", expired=" + isExpired() +
               '}';
    }
}
