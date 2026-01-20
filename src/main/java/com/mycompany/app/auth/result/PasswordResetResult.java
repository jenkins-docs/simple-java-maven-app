package com.mycompany.app.auth.result;

/**
 * Result object for password reset operations.
 * Contains success/failure status and relevant data for password reset attempts.
 * 
 * This class provides a clean API for handling password reset results with
 * static factory methods for success and failure cases.
 */
public class PasswordResetResult {
    private final boolean successful;
    private final String token;
    private final String errorMessage;

    /**
     * Private constructor to enforce use of factory methods.
     * 
     * @param successful whether the password reset was successful
     * @param token the reset token for successful reset initiation (null for failures)
     * @param errorMessage the error message for failed reset (null for success)
     */
    private PasswordResetResult(boolean successful, String token, String errorMessage) {
        this.successful = successful;
        this.token = token;
        this.errorMessage = errorMessage;
    }

    /**
     * Creates a successful password reset result with a reset token.
     * 
     * @param token the password reset token
     * @return a successful PasswordResetResult
     * @throws IllegalArgumentException if token is null or empty
     */
    public static PasswordResetResult success(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Token cannot be null or empty for successful password reset");
        }
        return new PasswordResetResult(true, token, null);
    }

    /**
     * Creates a failed password reset result with an error message.
     * 
     * @param errorMessage the error message describing why password reset failed
     * @return a failed PasswordResetResult
     * @throws IllegalArgumentException if errorMessage is null or empty
     */
    public static PasswordResetResult failure(String errorMessage) {
        if (errorMessage == null || errorMessage.trim().isEmpty()) {
            throw new IllegalArgumentException("Error message cannot be null or empty for failed password reset");
        }
        return new PasswordResetResult(false, null, errorMessage);
    }

    /**
     * Checks if the password reset was successful.
     * 
     * @return true if password reset succeeded, false otherwise
     */
    public boolean isSuccessful() {
        return successful;
    }

    /**
     * Gets the reset token for successful password reset initiation.
     * 
     * @return the reset token, or null if password reset failed
     */
    public String getToken() {
        return token;
    }

    /**
     * Gets the error message for failed password reset.
     * 
     * @return the error message, or null if password reset succeeded
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        if (successful) {
            return "PasswordResetResult{successful=true, token='" + token + "'}";
        } else {
            return "PasswordResetResult{successful=false, errorMessage='" + errorMessage + "'}";
        }
    }
}