package com.mycompany.app.auth.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * User entity representing system users.
 * Stores user credentials and metadata for authentication purposes.
 */
public class User {
    private String username;
    private String hashedPassword;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;

    /**
     * Creates a new User with the specified credentials and timestamps.
     *
     * @param username the unique username for this user
     * @param hashedPassword the BCrypt hashed password
     * @param createdAt the timestamp when the user was created
     * @param lastLoginAt the timestamp of the user's last login (can be null)
     */
    public User(String username, String hashedPassword, LocalDateTime createdAt, LocalDateTime lastLoginAt) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.createdAt = createdAt;
        this.lastLoginAt = lastLoginAt;
    }

    /**
     * Creates a new User with the specified credentials.
     * Sets createdAt to current time and lastLoginAt to null.
     *
     * @param username the unique username for this user
     * @param hashedPassword the BCrypt hashed password
     */
    public User(String username, String hashedPassword) {
        this(username, hashedPassword, LocalDateTime.now(), null);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) &&
               Objects.equals(hashedPassword, user.hashedPassword) &&
               Objects.equals(createdAt, user.createdAt) &&
               Objects.equals(lastLoginAt, user.lastLoginAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, hashedPassword, createdAt, lastLoginAt);
    }

    @Override
    public String toString() {
        return "User{" +
               "username='" + username + '\'' +
               ", hashedPassword='[PROTECTED]'" +
               ", createdAt=" + createdAt +
               ", lastLoginAt=" + lastLoginAt +
               '}';
    }
}
