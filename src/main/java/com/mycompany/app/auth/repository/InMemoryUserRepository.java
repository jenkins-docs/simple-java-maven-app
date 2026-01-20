package com.mycompany.app.auth.repository;

import com.mycompany.app.auth.model.User;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread-safe in-memory implementation of UserRepository.
 * Uses ConcurrentHashMap to ensure thread-safe operations for user storage.
 * Suitable for development and single-instance deployments.
 */
public class InMemoryUserRepository implements UserRepository {
    private final ConcurrentHashMap<String, User> users;

    /**
     * Creates a new InMemoryUserRepository with an empty user store.
     */
    public InMemoryUserRepository() {
        this.users = new ConcurrentHashMap<>();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        if (username == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(users.get(username));
    }

    @Override
    public void save(User user) {
        if (user == null || user.getUsername() == null) {
            throw new IllegalArgumentException("User and username cannot be null");
        }
        users.put(user.getUsername(), user);
    }

    @Override
    public void updatePassword(String username, String hashedPassword) {
        if (username == null || hashedPassword == null) {
            throw new IllegalArgumentException("Username and hashedPassword cannot be null");
        }
        
        users.computeIfPresent(username, (key, user) -> {
            user.setHashedPassword(hashedPassword);
            return user;
        });
    }
}
