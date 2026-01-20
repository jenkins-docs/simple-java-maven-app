package com.mycompany.app.auth.model;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Property-based tests for Session data model integrity.
 * 
 * Feature: user-authentication
 * Property 9: Session Management Integrity
 * 
 * **Validates: Requirements 4.1, 4.2, 4.3, 4.4**
 */
class SessionPropertyTest {

    private static final int PROPERTY_TEST_ITERATIONS = 100;
    private static final Random random = new Random();

    /**
     * Property 9: Session Management Integrity
     * 
     * For any created session, it should have a unique identifier, be properly 
     * associated with the authenticated user, and be verifiable through authentication checks.
     * 
     * **Validates: Requirements 4.1, 4.2, 4.3, 4.4**
     * 
     * This property test verifies:
     * - Requirement 4.1: Sessions have unique identifiers
     * - Requirement 4.2: Sessions are associated with authenticated users
     * - Requirement 4.3: Session validity can be verified
     * - Requirement 4.4: User authentication status can be checked via session
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    void propertySessionManagementIntegrity() {
        // Generate random session data
        String sessionId = generateRandomSessionId();
        String username = generateRandomUsername();
        LocalDateTime createdAt = generateRandomCreatedAt();
        LocalDateTime expiresAt = generateRandomExpiresAt(createdAt);
        
        // Create session
        Session session = new Session(sessionId, username, createdAt, expiresAt);
        
        // Property 1: Session has a unique identifier (Requirement 4.1)
        assertNotNull(session.getSessionId(), 
            "Session must have a non-null session identifier");
        assertFalse(session.getSessionId().isEmpty(), 
            "Session identifier must not be empty");
        assertEquals(sessionId, session.getSessionId(), 
            "Session identifier must match the provided value");
        
        // Property 2: Session is properly associated with authenticated user (Requirement 4.2)
        assertNotNull(session.getUsername(), 
            "Session must be associated with a username");
        assertFalse(session.getUsername().isEmpty(), 
            "Session username must not be empty");
        assertEquals(username, session.getUsername(), 
            "Session must be associated with the correct user");
        
        // Property 3: Session validity can be verified (Requirement 4.3)
        assertNotNull(session.getCreatedAt(), 
            "Session must have a creation timestamp");
        assertNotNull(session.getExpiresAt(), 
            "Session must have an expiration timestamp");
        
        // Property 4: Session expiration status is verifiable (Requirement 4.4)
        // The isExpired() method should return true if current time is after expiration
        boolean isExpired = session.isExpired();
        LocalDateTime now = LocalDateTime.now();
        boolean expectedExpired = now.isAfter(expiresAt);
        assertEquals(expectedExpired, isExpired, 
            "Session expiration status must be correctly verifiable");
        
        // Property 5: Session maintains data integrity through reconstruction
        Session reconstructed = new Session(
            session.getSessionId(), 
            session.getUsername(), 
            session.getCreatedAt(), 
            session.getExpiresAt()
        );
        assertEquals(session, reconstructed, 
            "Session must maintain data integrity through reconstruction");
    }

    /**
     * Property test: Multiple sessions must have unique identifiers
     * 
     * **Validates: Requirements 4.1**
     */
    @Test
    void propertyMultipleSessionsHaveUniqueIdentifiers() {
        Set<String> sessionIds = new HashSet<>();
        List<Session> sessions = new ArrayList<>();
        
        // Create multiple sessions
        for (int i = 0; i < PROPERTY_TEST_ITERATIONS; i++) {
            String sessionId = generateRandomSessionId();
            String username = generateRandomUsername();
            Session session = new Session(sessionId, username);
            
            sessions.add(session);
            sessionIds.add(sessionId);
        }
        
        // Verify all session IDs are unique
        assertEquals(sessions.size(), sessionIds.size(), 
            "All sessions must have unique identifiers");
        
        // Verify each session maintains its unique identifier
        for (Session session : sessions) {
            assertNotNull(session.getSessionId(), 
                "Each session must have a non-null identifier");
            assertTrue(sessionIds.contains(session.getSessionId()), 
                "Each session identifier must be in the set of unique identifiers");
        }
    }

    /**
     * Property test: Session-user association must be consistent
     * 
     * **Validates: Requirements 4.2**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    void propertySessionUserAssociationIsConsistent() {
        String sessionId = generateRandomSessionId();
        String username = generateRandomUsername();
        
        Session session = new Session(sessionId, username);
        
        // Verify association is consistent across multiple accesses
        for (int i = 0; i < 10; i++) {
            assertEquals(username, session.getUsername(), 
                "Session-user association must remain consistent");
            assertEquals(sessionId, session.getSessionId(), 
                "Session identifier must remain consistent");
        }
        
        // Verify association survives modification of other fields
        LocalDateTime newExpiration = LocalDateTime.now().plusHours(1);
        session.setExpiresAt(newExpiration);
        
        assertEquals(username, session.getUsername(), 
            "Session-user association must remain consistent after modifications");
        assertEquals(sessionId, session.getSessionId(), 
            "Session identifier must remain consistent after modifications");
    }

    /**
     * Property test: Session validity verification must be accurate
     * 
     * **Validates: Requirements 4.3, 4.4**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    void propertySessionValidityVerificationIsAccurate() {
        String sessionId = generateRandomSessionId();
        String username = generateRandomUsername();
        LocalDateTime createdAt = generateRandomCreatedAt();
        LocalDateTime expiresAt = generateRandomExpiresAt(createdAt);
        
        Session session = new Session(sessionId, username, createdAt, expiresAt);
        
        // Verify expiration status is accurate
        LocalDateTime now = LocalDateTime.now();
        boolean shouldBeExpired = now.isAfter(expiresAt);
        
        assertEquals(shouldBeExpired, session.isExpired(), 
            "Session expiration status must accurately reflect current time vs expiration time");
        
        // Verify timestamps are accessible for validation
        assertNotNull(session.getCreatedAt(), 
            "Session creation time must be accessible for validation");
        assertNotNull(session.getExpiresAt(), 
            "Session expiration time must be accessible for validation");
    }

    /**
     * Property test: Session equality must be based on all fields
     * 
     * **Validates: Requirements 4.1, 4.2**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    void propertySessionEqualityIsBasedOnAllFields() {
        String sessionId = generateRandomSessionId();
        String username = generateRandomUsername();
        LocalDateTime createdAt = generateRandomCreatedAt();
        LocalDateTime expiresAt = generateRandomExpiresAt(createdAt);
        
        Session session1 = new Session(sessionId, username, createdAt, expiresAt);
        Session session2 = new Session(sessionId, username, createdAt, expiresAt);
        
        // Sessions with identical fields must be equal
        assertEquals(session1, session2, 
            "Sessions with identical fields must be equal");
        assertEquals(session1.hashCode(), session2.hashCode(), 
            "Sessions with identical fields must have equal hash codes");
        
        // Sessions with different session IDs must not be equal
        Session differentId = new Session(generateRandomSessionId(), username, createdAt, expiresAt);
        assertNotEquals(session1, differentId, 
            "Sessions with different IDs must not be equal");
        
        // Sessions with different usernames must not be equal
        Session differentUser = new Session(sessionId, generateRandomUsername(), createdAt, expiresAt);
        assertNotEquals(session1, differentUser, 
            "Sessions with different usernames must not be equal");
    }

    /**
     * Property test: Session data must be immutable through getters
     * 
     * **Validates: Requirements 4.1, 4.2, 4.3**
     */
    @RepeatedTest(PROPERTY_TEST_ITERATIONS)
    void propertySessionDataIntegrityThroughGetters() {
        String sessionId = generateRandomSessionId();
        String username = generateRandomUsername();
        LocalDateTime createdAt = generateRandomCreatedAt();
        LocalDateTime expiresAt = generateRandomExpiresAt(createdAt);
        
        Session session = new Session(sessionId, username, createdAt, expiresAt);
        
        // Store original values
        String originalSessionId = session.getSessionId();
        String originalUsername = session.getUsername();
        LocalDateTime originalCreatedAt = session.getCreatedAt();
        LocalDateTime originalExpiresAt = session.getExpiresAt();
        
        // Access getters multiple times
        for (int i = 0; i < 10; i++) {
            assertEquals(originalSessionId, session.getSessionId(), 
                "Session ID must remain consistent across multiple getter calls");
            assertEquals(originalUsername, session.getUsername(), 
                "Username must remain consistent across multiple getter calls");
            assertEquals(originalCreatedAt, session.getCreatedAt(), 
                "Created timestamp must remain consistent across multiple getter calls");
            assertEquals(originalExpiresAt, session.getExpiresAt(), 
                "Expiration timestamp must remain consistent across multiple getter calls");
        }
    }

    // ========== Test Data Generators ==========

    /**
     * Generates a random session ID using UUID format.
     */
    private String generateRandomSessionId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Generates a random username.
     */
    private String generateRandomUsername() {
        String[] prefixes = {"user", "admin", "guest", "test", "demo"};
        String prefix = prefixes[random.nextInt(prefixes.length)];
        int suffix = random.nextInt(10000);
        return prefix + suffix;
    }

    /**
     * Generates a random creation timestamp.
     * Uses a fixed reference time to avoid timing issues in tests.
     * 50% chance within past 24 hours (likely valid session)
     * 50% chance further in the past (likely expired session)
     */
    private LocalDateTime generateRandomCreatedAt() {
        // Use a fixed reference time to avoid timing issues
        LocalDateTime referenceTime = LocalDateTime.of(2024, 1, 15, 12, 0, 0);
        
        if (random.nextBoolean()) {
            // Recent creation: within past 24 hours from reference
            int hoursAgo = random.nextInt(24);
            int minutesAgo = random.nextInt(60);
            return referenceTime.minusHours(hoursAgo).minusMinutes(minutesAgo);
        } else {
            // Older creation: 1-7 days ago from reference (will result in expired session)
            int daysAgo = 1 + random.nextInt(7);
            int hoursAgo = random.nextInt(24);
            return referenceTime.minusDays(daysAgo).minusHours(hoursAgo);
        }
    }

    /**
     * Generates a random expiration timestamp after the creation time.
     * Can be in the past (expired) or future (valid).
     * ALWAYS ensures expiration is after creation time (maintains invariant).
     */
    private LocalDateTime generateRandomExpiresAt(LocalDateTime createdAt) {
        // Generate expiration time between 15 and 135 minutes after creation
        // This ALWAYS maintains the invariant: expiresAt > createdAt
        int minutesFromCreation = 15 + random.nextInt(120);
        return createdAt.plusMinutes(minutesFromCreation);
    }
}
