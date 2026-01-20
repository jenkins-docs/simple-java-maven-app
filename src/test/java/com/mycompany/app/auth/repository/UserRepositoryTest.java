package com.mycompany.app.auth.repository;

import com.mycompany.app.auth.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UserRepository implementations.
 * Contains unit tests for user data operations.
 */
@DisplayName("InMemoryUserRepository Tests")
public class UserRepositoryTest {
    
    private InMemoryUserRepository repository;
    
    @BeforeEach
    void setUp() {
        repository = new InMemoryUserRepository();
    }
    
    @Test
    @DisplayName("Should save and retrieve user by username")
    void testSaveAndFindByUsername() {
        // Arrange
        User user = new User("testuser", "hashedPassword123", LocalDateTime.now(), null);
        
        // Act
        repository.save(user);
        Optional<User> found = repository.findByUsername("testuser");
        
        // Assert
        assertTrue(found.isPresent(), "User should be found");
        assertEquals("testuser", found.get().getUsername());
        assertEquals("hashedPassword123", found.get().getHashedPassword());
    }
    
    @Test
    @DisplayName("Should return empty Optional when user not found")
    void testFindByUsernameNotFound() {
        // Act
        Optional<User> found = repository.findByUsername("nonexistent");
        
        // Assert
        assertFalse(found.isPresent(), "User should not be found");
    }
    
    @Test
    @DisplayName("Should return empty Optional when username is null")
    void testFindByUsernameNull() {
        // Act
        Optional<User> found = repository.findByUsername(null);
        
        // Assert
        assertFalse(found.isPresent(), "Should return empty Optional for null username");
    }
    
    @Test
    @DisplayName("Should replace existing user when saving with same username")
    void testSaveReplacesExistingUser() {
        // Arrange
        User user1 = new User("testuser", "password1", LocalDateTime.now(), null);
        User user2 = new User("testuser", "password2", LocalDateTime.now(), null);
        
        // Act
        repository.save(user1);
        repository.save(user2);
        Optional<User> found = repository.findByUsername("testuser");
        
        // Assert
        assertTrue(found.isPresent());
        assertEquals("password2", found.get().getHashedPassword());
    }
    
    @Test
    @DisplayName("Should throw exception when saving null user")
    void testSaveNullUser() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> repository.save(null));
    }
    
    @Test
    @DisplayName("Should throw exception when saving user with null username")
    void testSaveUserWithNullUsername() {
        // Arrange
        User user = new User(null, "hashedPassword");
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> repository.save(user));
    }
    
    @Test
    @DisplayName("Should update password for existing user")
    void testUpdatePassword() {
        // Arrange
        User user = new User("testuser", "oldPassword", LocalDateTime.now(), null);
        repository.save(user);
        
        // Act
        repository.updatePassword("testuser", "newPassword");
        Optional<User> found = repository.findByUsername("testuser");
        
        // Assert
        assertTrue(found.isPresent());
        assertEquals("newPassword", found.get().getHashedPassword());
    }
    
    @Test
    @DisplayName("Should do nothing when updating password for non-existent user")
    void testUpdatePasswordNonExistentUser() {
        // Act - should not throw exception
        repository.updatePassword("nonexistent", "newPassword");
        
        // Assert
        Optional<User> found = repository.findByUsername("nonexistent");
        assertFalse(found.isPresent());
    }
    
    @Test
    @DisplayName("Should throw exception when updating password with null username")
    void testUpdatePasswordNullUsername() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, 
            () -> repository.updatePassword(null, "newPassword"));
    }
    
    @Test
    @DisplayName("Should throw exception when updating password with null password")
    void testUpdatePasswordNullPassword() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, 
            () -> repository.updatePassword("testuser", null));
    }
    
    @Test
    @DisplayName("Should handle concurrent save operations safely")
    void testConcurrentSaveOperations() throws InterruptedException {
        // Arrange
        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);
        
        // Act
        for (int i = 0; i < threadCount; i++) {
            final int userId = i;
            executor.submit(() -> {
                try {
                    User user = new User("user" + userId, "password" + userId);
                    repository.save(user);
                    successCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }
        
        latch.await(5, TimeUnit.SECONDS);
        executor.shutdown();
        
        // Assert
        assertEquals(threadCount, successCount.get(), "All save operations should succeed");
        
        // Verify all users were saved
        for (int i = 0; i < threadCount; i++) {
            Optional<User> found = repository.findByUsername("user" + i);
            assertTrue(found.isPresent(), "User" + i + " should be found");
        }
    }
    
    @Test
    @DisplayName("Should handle concurrent password updates safely")
    void testConcurrentPasswordUpdates() throws InterruptedException {
        // Arrange
        User user = new User("testuser", "initialPassword");
        repository.save(user);
        
        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        
        // Act
        for (int i = 0; i < threadCount; i++) {
            final int updateId = i;
            executor.submit(() -> {
                try {
                    repository.updatePassword("testuser", "password" + updateId);
                } finally {
                    latch.countDown();
                }
            });
        }
        
        latch.await(5, TimeUnit.SECONDS);
        executor.shutdown();
        
        // Assert
        Optional<User> found = repository.findByUsername("testuser");
        assertTrue(found.isPresent(), "User should still exist");
        assertNotNull(found.get().getHashedPassword(), "Password should be set");
        assertTrue(found.get().getHashedPassword().startsWith("password"), 
            "Password should be one of the updated values");
    }
}
