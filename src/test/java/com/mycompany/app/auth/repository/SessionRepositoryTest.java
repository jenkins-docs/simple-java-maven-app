package com.mycompany.app.auth.repository;

import com.mycompany.app.auth.model.Session;
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
 * Test class for SessionRepository implementations.
 * Contains unit tests for session management operations.
 */
@DisplayName("InMemorySessionRepository Tests")
public class SessionRepositoryTest {
    
    private InMemorySessionRepository repository;
    
    @BeforeEach
    void setUp() {
        repository = new InMemorySessionRepository();
    }
    
    @Test
    @DisplayName("Should create session and retrieve user by session ID")
    void testCreateSessionAndGetUser() {
        // Act
        repository.createSession("session123", "testuser");
        Optional<String> username = repository.getUser("session123");
        
        // Assert
        assertTrue(username.isPresent(), "Username should be found");
        assertEquals("testuser", username.get());
    }
    
    @Test
    @DisplayName("Should create session with Session object")
    void testCreateSessionWithObject() {
        // Arrange
        Session session = new Session("session456", "testuser");
        
        // Act
        repository.createSession(session);
        Optional<Session> found = repository.findBySessionId("session456");
        
        // Assert
        assertTrue(found.isPresent(), "Session should be found");
        assertEquals("session456", found.get().getSessionId());
        assertEquals("testuser", found.get().getUsername());
    }
    
    @Test
    @DisplayName("Should return empty Optional when session not found")
    void testGetUserSessionNotFound() {
        // Act
        Optional<String> username = repository.getUser("nonexistent");
        
        // Assert
        assertFalse(username.isPresent(), "Username should not be found");
    }
    
    @Test
    @DisplayName("Should return empty Optional when session ID is null")
    void testGetUserNullSessionId() {
        // Act
        Optional<String> username = repository.getUser(null);
        
        // Assert
        assertFalse(username.isPresent(), "Should return empty Optional for null session ID");
    }
    
    @Test
    @DisplayName("Should return empty Optional when session ID is empty")
    void testGetUserEmptySessionId() {
        // Act
        Optional<String> username = repository.getUser("");
        
        // Assert
        assertFalse(username.isPresent(), "Should return empty Optional for empty session ID");
    }
    
    @Test
    @DisplayName("Should throw exception when creating session with null session ID")
    void testCreateSessionNullSessionId() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, 
            () -> repository.createSession(null, "testuser"));
    }
    
    @Test
    @DisplayName("Should throw exception when creating session with empty session ID")
    void testCreateSessionEmptySessionId() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, 
            () -> repository.createSession("", "testuser"));
    }
    
    @Test
    @DisplayName("Should throw exception when creating session with null username")
    void testCreateSessionNullUsername() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, 
            () -> repository.createSession("session123", null));
    }
    
    @Test
    @DisplayName("Should throw exception when creating session with null Session object")
    void testCreateSessionNullObject() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, 
            () -> repository.createSession((Session) null));
    }
    
    @Test
    @DisplayName("Should invalidate session")
    void testInvalidateSession() {
        // Arrange
        repository.createSession("session123", "testuser");
        
        // Act
        repository.invalidateSession("session123");
        Optional<String> username = repository.getUser("session123");
        
        // Assert
        assertFalse(username.isPresent(), "Session should be invalidated");
    }
    
    @Test
    @DisplayName("Should handle invalidating non-existent session gracefully")
    void testInvalidateNonExistentSession() {
        // Act - should not throw exception
        repository.invalidateSession("nonexistent");
        
        // Assert - no exception thrown
        assertTrue(true);
    }
    
    @Test
    @DisplayName("Should handle invalidating with null session ID gracefully")
    void testInvalidateNullSessionId() {
        // Act - should not throw exception
        repository.invalidateSession(null);
        
        // Assert - no exception thrown
        assertTrue(true);
    }
    
    @Test
    @DisplayName("Should return empty Optional for expired session")
    void testGetUserExpiredSession() {
        // Arrange - create session that expires immediately
        LocalDateTime now = LocalDateTime.now();
        Session expiredSession = new Session("session123", "testuser", 
            now.minusMinutes(31), now.minusMinutes(1));
        repository.createSession(expiredSession);
        
        // Act
        Optional<String> username = repository.getUser("session123");
        
        // Assert
        assertFalse(username.isPresent(), "Expired session should not return username");
    }
    
    @Test
    @DisplayName("Should cleanup expired sessions")
    void testCleanupExpiredSessions() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        
        // Create valid session
        Session validSession = new Session("valid123", "user1");
        repository.createSession(validSession);
        
        // Create expired session
        Session expiredSession = new Session("expired123", "user2", 
            now.minusMinutes(31), now.minusMinutes(1));
        repository.createSession(expiredSession);
        
        // Act
        repository.cleanupExpiredSessions();
        
        // Assert
        assertTrue(repository.findBySessionId("valid123").isPresent(), 
            "Valid session should still exist");
        assertFalse(repository.findBySessionId("expired123").isPresent(), 
            "Expired session should be removed");
    }
    
    @Test
    @DisplayName("Should validate active session as valid")
    void testIsValidActiveSession() {
        // Arrange
        repository.createSession("session123", "testuser");
        
        // Act
        boolean isValid = repository.isValid("session123");
        
        // Assert
        assertTrue(isValid, "Active session should be valid");
    }
    
    @Test
    @DisplayName("Should validate expired session as invalid")
    void testIsValidExpiredSession() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        Session expiredSession = new Session("session123", "testuser", 
            now.minusMinutes(31), now.minusMinutes(1));
        repository.createSession(expiredSession);
        
        // Act
        boolean isValid = repository.isValid("session123");
        
        // Assert
        assertFalse(isValid, "Expired session should be invalid");
    }
    
    @Test
    @DisplayName("Should validate non-existent session as invalid")
    void testIsValidNonExistentSession() {
        // Act
        boolean isValid = repository.isValid("nonexistent");
        
        // Assert
        assertFalse(isValid, "Non-existent session should be invalid");
    }
    
    @Test
    @DisplayName("Should validate null session ID as invalid")
    void testIsValidNullSessionId() {
        // Act
        boolean isValid = repository.isValid(null);
        
        // Assert
        assertFalse(isValid, "Null session ID should be invalid");
    }
    
    @Test
    @DisplayName("Should handle concurrent session creation safely")
    void testConcurrentSessionCreation() throws InterruptedException {
        // Arrange
        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);
        
        // Act
        for (int i = 0; i < threadCount; i++) {
            final int sessionId = i;
            executor.submit(() -> {
                try {
                    repository.createSession("session" + sessionId, "user" + sessionId);
                    successCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }
        
        latch.await(5, TimeUnit.SECONDS);
        executor.shutdown();
        
        // Assert
        assertEquals(threadCount, successCount.get(), "All session creation operations should succeed");
        
        // Verify all sessions were created
        for (int i = 0; i < threadCount; i++) {
            Optional<String> username = repository.getUser("session" + i);
            assertTrue(username.isPresent(), "Session" + i + " should exist");
            assertEquals("user" + i, username.get());
        }
    }
    
    @Test
    @DisplayName("Should handle concurrent session invalidation safely")
    void testConcurrentSessionInvalidation() throws InterruptedException {
        // Arrange
        int threadCount = 10;
        for (int i = 0; i < threadCount; i++) {
            repository.createSession("session" + i, "user" + i);
        }
        
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        
        // Act
        for (int i = 0; i < threadCount; i++) {
            final int sessionId = i;
            executor.submit(() -> {
                try {
                    repository.invalidateSession("session" + sessionId);
                } finally {
                    latch.countDown();
                }
            });
        }
        
        latch.await(5, TimeUnit.SECONDS);
        executor.shutdown();
        
        // Assert
        for (int i = 0; i < threadCount; i++) {
            Optional<String> username = repository.getUser("session" + i);
            assertFalse(username.isPresent(), "Session" + i + " should be invalidated");
        }
    }
    
    @Test
    @DisplayName("Should handle concurrent cleanup operations safely")
    void testConcurrentCleanupOperations() throws InterruptedException {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        
        // Create mix of valid and expired sessions
        for (int i = 0; i < 5; i++) {
            repository.createSession("valid" + i, "user" + i);
        }
        for (int i = 0; i < 5; i++) {
            Session expiredSession = new Session("expired" + i, "user" + i, 
                now.minusMinutes(31), now.minusMinutes(1));
            repository.createSession(expiredSession);
        }
        
        int threadCount = 5;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        
        // Act - multiple threads cleaning up simultaneously
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    repository.cleanupExpiredSessions();
                } finally {
                    latch.countDown();
                }
            });
        }
        
        latch.await(5, TimeUnit.SECONDS);
        executor.shutdown();
        
        // Assert
        for (int i = 0; i < 5; i++) {
            assertTrue(repository.findBySessionId("valid" + i).isPresent(), 
                "Valid session" + i + " should still exist");
            assertFalse(repository.findBySessionId("expired" + i).isPresent(), 
                "Expired session" + i + " should be removed");
        }
    }
}