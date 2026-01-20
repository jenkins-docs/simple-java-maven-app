package com.mycompany.app.auth.result;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for AuthenticationResult.
 * Tests the result object for authentication operations including success/failure states,
 * static factory methods, and validation.
 */
public class AuthenticationResultTest {

    @Test
    public void testSuccessfulAuthentication() {
        // Given
        String sessionId = "test-session-123";
        
        // When
        AuthenticationResult result = AuthenticationResult.success(sessionId);
        
        // Then
        assertTrue(result.isSuccessful(), "Result should be successful");
        assertEquals(sessionId, result.getSessionId(), "Session ID should match");
        assertNull(result.getErrorMessage(), "Error message should be null for success");
    }

    @Test
    public void testFailedAuthentication() {
        // Given
        String errorMessage = "Invalid credentials";
        
        // When
        AuthenticationResult result = AuthenticationResult.failure(errorMessage);
        
        // Then
        assertFalse(result.isSuccessful(), "Result should not be successful");
        assertNull(result.getSessionId(), "Session ID should be null for failure");
        assertEquals(errorMessage, result.getErrorMessage(), "Error message should match");
    }

    @Test
    public void testSuccessWithNullSessionIdThrowsException() {
        // When/Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> AuthenticationResult.success(null),
            "Should throw exception for null session ID"
        );
        assertTrue(exception.getMessage().contains("Session ID cannot be null or empty"));
    }

    @Test
    public void testSuccessWithEmptySessionIdThrowsException() {
        // When/Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> AuthenticationResult.success(""),
            "Should throw exception for empty session ID"
        );
        assertTrue(exception.getMessage().contains("Session ID cannot be null or empty"));
    }

    @Test
    public void testSuccessWithWhitespaceSessionIdThrowsException() {
        // When/Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> AuthenticationResult.success("   "),
            "Should throw exception for whitespace-only session ID"
        );
        assertTrue(exception.getMessage().contains("Session ID cannot be null or empty"));
    }

    @Test
    public void testFailureWithNullErrorMessageThrowsException() {
        // When/Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> AuthenticationResult.failure(null),
            "Should throw exception for null error message"
        );
        assertTrue(exception.getMessage().contains("Error message cannot be null or empty"));
    }

    @Test
    public void testFailureWithEmptyErrorMessageThrowsException() {
        // When/Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> AuthenticationResult.failure(""),
            "Should throw exception for empty error message"
        );
        assertTrue(exception.getMessage().contains("Error message cannot be null or empty"));
    }

    @Test
    public void testFailureWithWhitespaceErrorMessageThrowsException() {
        // When/Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> AuthenticationResult.failure("   "),
            "Should throw exception for whitespace-only error message"
        );
        assertTrue(exception.getMessage().contains("Error message cannot be null or empty"));
    }

    @Test
    public void testToStringForSuccess() {
        // Given
        String sessionId = "session-abc-123";
        AuthenticationResult result = AuthenticationResult.success(sessionId);
        
        // When
        String toString = result.toString();
        
        // Then
        assertTrue(toString.contains("successful=true"), "toString should indicate success");
        assertTrue(toString.contains(sessionId), "toString should contain session ID");
        assertFalse(toString.contains("errorMessage"), "toString should not contain error message for success");
    }

    @Test
    public void testToStringForFailure() {
        // Given
        String errorMessage = "Authentication failed";
        AuthenticationResult result = AuthenticationResult.failure(errorMessage);
        
        // When
        String toString = result.toString();
        
        // Then
        assertTrue(toString.contains("successful=false"), "toString should indicate failure");
        assertTrue(toString.contains(errorMessage), "toString should contain error message");
        assertFalse(toString.contains("sessionId"), "toString should not contain session ID for failure");
    }

    @Test
    public void testSuccessWithLongSessionId() {
        // Given - test with a realistic UUID-style session ID
        String sessionId = "550e8400-e29b-41d4-a716-446655440000";
        
        // When
        AuthenticationResult result = AuthenticationResult.success(sessionId);
        
        // Then
        assertTrue(result.isSuccessful());
        assertEquals(sessionId, result.getSessionId());
    }

    @Test
    public void testFailureWithLongErrorMessage() {
        // Given - test with a detailed error message
        String errorMessage = "Authentication failed: Invalid username or password. Please check your credentials and try again.";
        
        // When
        AuthenticationResult result = AuthenticationResult.failure(errorMessage);
        
        // Then
        assertFalse(result.isSuccessful());
        assertEquals(errorMessage, result.getErrorMessage());
    }

    @Test
    public void testMultipleSuccessResultsAreIndependent() {
        // Given
        String sessionId1 = "session-1";
        String sessionId2 = "session-2";
        
        // When
        AuthenticationResult result1 = AuthenticationResult.success(sessionId1);
        AuthenticationResult result2 = AuthenticationResult.success(sessionId2);
        
        // Then
        assertEquals(sessionId1, result1.getSessionId());
        assertEquals(sessionId2, result2.getSessionId());
        assertNotEquals(result1.getSessionId(), result2.getSessionId());
    }

    @Test
    public void testMultipleFailureResultsAreIndependent() {
        // Given
        String error1 = "Invalid credentials";
        String error2 = "Account locked";
        
        // When
        AuthenticationResult result1 = AuthenticationResult.failure(error1);
        AuthenticationResult result2 = AuthenticationResult.failure(error2);
        
        // Then
        assertEquals(error1, result1.getErrorMessage());
        assertEquals(error2, result2.getErrorMessage());
        assertNotEquals(result1.getErrorMessage(), result2.getErrorMessage());
    }
}
