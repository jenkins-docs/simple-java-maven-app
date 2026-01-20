package com.mycompany.app.auth.result;

/**
 * Result object for login operations.
 * Contains success/failure status and relevant data for authentication attempts.
 * 
 * This class provides a clean API for handling authentication results with
 * static factory methods for success and failure cases.
 */
public class AuthenticationResult {
    private final boolean successful;
    private final String sessionId;
    private final String errorMessage;

    /**
     * Private constructor to enforce use of factory methods.
     * 
     * @param successful whether the authentication was successful
     * @param sessionId the session ID for successful authentication (null for failures)
     * @param errorMessage the error message for failed authentication (null for success)
     */
    private AuthenticationResult(boolean successful, String sessionId, String errorMessage) {
        this.successful = successful;
        this.sessionId = sessionId;
        this.errorMessage = errorMessage;
    }

    /**
     * Creates a successful authentication result with a session ID.
     * 
     * @param sessionId the session ID for the authenticated user
     * @return a successful AuthenticationResult
     * @throws IllegalArgumentException if sessionId is null or empty
     */
    public static AuthenticationResult success(String sessionId) {
        if (sessionId == null || sessionId.trim().isEmpty()) {
            throw new IllegalArgumentException("Session ID cannot be null or empty for successful authentication");
        }
        return new AuthenticationResult(true, sessionId, null);
    }

    /**
     * Creates a failed authentication result with an error message.
     * 
     * @param errorMessage the error message describing why authentication failed
     * @return a failed AuthenticationResult
     * @throws IllegalArgumentException if errorMessage is null or empty
     */
    public static AuthenticationResult failure(String errorMessage) {
        if (errorMessage == null || errorMessage.trim().isEmpty()) {
            throw new IllegalArgumentException("Error message cannot be null or empty for failed authentication");
        }
        return new AuthenticationResult(false, null, errorMessage);
    }

    /**
     * Checks if the authentication was successful.
     * 
     * @return true if authentication succeeded, false otherwise
     */
    public boolean isSuccessful() {
        return successful;
    }

    /**
     * Gets the session ID for successful authentication.
     * 
     * @return the session ID, or null if authentication failed
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Gets the error message for failed authentication.
     * 
     * @return the error message, or null if authentication succeeded
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        if (successful) {
            return "AuthenticationResult{successful=true, sessionId='" + sessionId + "'}";
        } else {
            return "AuthenticationResult{successful=false, errorMessage='" + errorMessage + "'}";
        }
    }
}