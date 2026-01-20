package com.mycompany.app.auth.result;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for PasswordResetResult.
 * Tests the result object for password reset operations including success/failure states,
 * static factory methods, and validation.
 */
public class PasswordResetResultTest {

    @Test
    public void testSuccessfulPasswordReset() {
        // Given
        String token = "reset-token-abc123";
        
        // When
        PasswordResetResult result = PasswordResetResult.success(token);
        
        // Then
        assertTrue(result.isSuccessful(), "Result should be successful");
        assertEquals(token, result.getToken(), "Token should match");
        assertNull(result.getErrorMessage(), "Error message should be null for success");
    }

    @Test
    public void testFailedPasswordReset() {
        // Given
        String errorMessage = "User not found";
        
        // When
        PasswordResetResult result = PasswordResetResult.failure(errorMessage);
        
        // Then
        assertFalse(result.isSuccessful(), "Result should not be successful");
        assertNull(result.getToken(), "Token should be null for failure");
        assertEquals(errorMessage, result.getErrorMessage(), "Error message should match");
    }

    @Test
    public void testSuccessWithNullTokenThrowsException() {
        // When/Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> PasswordResetResult.success(null),
            "Should throw exception for null token"
        );
        assertTrue(exception.getMessage().contains("Token cannot be null or empty"));
    }

    @Test
    public void testSuccessWithEmptyTokenThrowsException() {
        // When/Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> PasswordResetResult.success(""),
            "Should throw exception for empty token"
        );
        assertTrue(exception.getMessage().contains("Token cannot be null or empty"));
    }

    @Test
    public void testSuccessWithWhitespaceTokenThrowsException() {
        // When/Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> PasswordResetResult.success("   "),
            "Should throw exception for whitespace-only token"
        );
        assertTrue(exception.getMessage().contains("Token cannot be null or empty"));
    }

    @Test
    public void testFailureWithNullErrorMessageThrowsException() {
        // When/Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> PasswordResetResult.failure(null),
            "Should throw exception for null error message"
        );
        assertTrue(exception.getMessage().contains("Error message cannot be null or empty"));
    }

    @Test
    public void testFailureWithEmptyErrorMessageThrowsException() {
        // When/Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> PasswordResetResult.failure(""),
            "Should throw exception for empty error message"
        );
        assertTrue(exception.getMessage().contains("Error message cannot be null or empty"));
    }

    @Test
    public void testFailureWithWhitespaceErrorMessageThrowsException() {
        // When/Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> PasswordResetResult.failure("   "),
            "Should throw exception for whitespace-only error message"
        );
        assertTrue(exception.getMessage().contains("Error message cannot be null or empty"));
    }

    @Test
    public void testToStringForSuccess() {
        // Given
        String token = "token-xyz-789";
        PasswordResetResult result = PasswordResetResult.success(token);
        
        // When
        String toString = result.toString();
        
        // Then
        assertTrue(toString.contains("successful=true"), "toString should indicate success");
        assertTrue(toString.contains(token), "toString should contain token");
        assertFalse(toString.contains("errorMessage"), "toString should not contain error message for success");
    }

    @Test
    public void testToStringForFailure() {
        // Given
        String errorMessage = "Password reset failed";
        PasswordResetResult result = PasswordResetResult.failure(errorMessage);
        
        // When
        String toString = result.toString();
        
        // Then
        assertTrue(toString.contains("successful=false"), "toString should indicate failure");
        assertTrue(toString.contains(errorMessage), "toString should contain error message");
        assertFalse(toString.contains("token"), "toString should not contain token for failure");
    }

    @Test
    public void testSuccessWithLongToken() {
        // Given - test with a realistic cryptographically secure token
        String token = "a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6q7r8s9t0u1v2w3x4y5z6";
        
        // When
        PasswordResetResult result = PasswordResetResult.success(token);
        
        // Then
        assertTrue(result.isSuccessful());
        assertEquals(token, result.getToken());
    }

    @Test
    public void testFailureWithLongErrorMessage() {
        // Given - test with a detailed error message
        String errorMessage = "Password reset failed: The provided username does not exist in the system. Please verify your username and try again.";
        
        // When
        PasswordResetResult result = PasswordResetResult.failure(errorMessage);
        
        // Then
        assertFalse(result.isSuccessful());
        assertEquals(errorMessage, result.getErrorMessage());
    }

    @Test
    public void testMultipleSuccessResultsAreIndependent() {
        // Given
        String token1 = "token-1";
        String token2 = "token-2";
        
        // When
        PasswordResetResult result1 = PasswordResetResult.success(token1);
        PasswordResetResult result2 = PasswordResetResult.success(token2);
        
        // Then
        assertEquals(token1, result1.getToken());
        assertEquals(token2, result2.getToken());
        assertNotEquals(result1.getToken(), result2.getToken());
    }

    @Test
    public void testMultipleFailureResultsAreIndependent() {
        // Given
        String error1 = "User not found";
        String error2 = "Token expired";
        
        // When
        PasswordResetResult result1 = PasswordResetResult.failure(error1);
        PasswordResetResult result2 = PasswordResetResult.failure(error2);
        
        // Then
        assertEquals(error1, result1.getErrorMessage());
        assertEquals(error2, result2.getErrorMessage());
        assertNotEquals(result1.getErrorMessage(), result2.getErrorMessage());
    }

    @Test
    public void testSuccessWithSpecialCharactersInToken() {
        // Given - test with token containing special characters
        String token = "token-123_ABC!@#$%";
        
        // When
        PasswordResetResult result = PasswordResetResult.success(token);
        
        // Then
        assertTrue(result.isSuccessful());
        assertEquals(token, result.getToken());
    }

    @Test
    public void testFailureWithSpecialCharactersInErrorMessage() {
        // Given - test with error message containing special characters
        String errorMessage = "Error: Invalid request! Please try again.";
        
        // When
        PasswordResetResult result = PasswordResetResult.failure(errorMessage);
        
        // Then
        assertFalse(result.isSuccessful());
        assertEquals(errorMessage, result.getErrorMessage());
    }
}
