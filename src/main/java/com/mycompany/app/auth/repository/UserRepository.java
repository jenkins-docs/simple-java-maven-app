package com.mycompany.app.auth.repository;

import com.mycompany.app.auth.model.User;
import java.util.Optional;

/**
 * Repository interface for user data operations.
 * Provides methods for user storage, retrieval, and password management.
 */
public interface UserRepository {
    /**
     * Finds a user by their username.
     *
     * @param username the username to search for
     * @return an Optional containing the user if found, or empty if not found
     */
    Optional<User> findByUsername(String username);

    /**
     * Saves a user to the repository.
     * If a user with the same username already exists, it will be replaced.
     *
     * @param user the user to save
     */
    void save(User user);

    /**
     * Updates the password for a user.
     *
     * @param username the username of the user to update
     * @param hashedPassword the new hashed password
     */
    void updatePassword(String username, String hashedPassword);
}