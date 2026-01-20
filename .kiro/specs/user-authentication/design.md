# Design Document: User Authentication System

## Overview

This design document outlines the technical architecture for a user authentication system in Java. The system provides secure login, logout, and password reset functionality using industry-standard security practices. The design leverages established Java libraries for cryptographic operations and follows object-oriented design principles for maintainability and extensibility.

The authentication system is designed as a modular component that can be integrated into the existing Java Maven application. It uses BCrypt for password hashing, secure random token generation for password resets, and in-memory session management suitable for single-instance applications.

## Architecture

The authentication system follows a layered architecture pattern:

```
┌─────────────────────────────────────┐
│           Application Layer         │
│  (Controllers, CLI, Web Endpoints)  │
└─────────────────────────────────────┘
                    │
┌─────────────────────────────────────┐
│          Service Layer              │
│     (AuthenticationService)         │
└─────────────────────────────────────┘
                    │
┌─────────────────────────────────────┐
│         Repository Layer            │
│  (UserRepository, SessionRepository)│
└─────────────────────────────────────┘
                    │
┌─────────────────────────────────────┐
│          Data Layer                 │
│    (In-Memory Storage/Database)     │
└─────────────────────────────────────┘
```

**Key Architectural Decisions:**
- **Separation of Concerns**: Clear separation between authentication logic, data access, and presentation
- **Dependency Injection Ready**: Interfaces allow for easy testing and future framework integration
- **Security First**: All security operations use established libraries and best practices
- **Extensible Design**: Easy to add new authentication methods or integrate with external systems

## Components and Interfaces

### Core Interfaces

```java
// Primary service interface for all authentication operations
public interface AuthenticationService {
    AuthenticationResult login(String username, String password);
    void logout(String sessionId);
    boolean isAuthenticated(String sessionId);
    String getCurrentUser(String sessionId);
    PasswordResetResult initiatePasswordReset(String username);
    boolean completePasswordReset(String token, String newPassword);
}

// Repository for user data operations
public interface UserRepository {
    Optional<User> findByUsername(String username);
    void save(User user);
    void updatePassword(String username, String hashedPassword);
}

// Repository for session management
public interface SessionRepository {
    void createSession(String sessionId, String username);
    Optional<String> getUser(String sessionId);
    void invalidateSession(String sessionId);
    void cleanupExpiredSessions();
}
```

### Core Data Models

```java
// User entity representing system users
public class User {
    private String username;
    private String hashedPassword;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    
    // Constructor, getters, setters, equals, hashCode
}

// Session entity for tracking authenticated users
public class Session {
    private String sessionId;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    
    // Constructor, getters, setters
}

// Password reset token for secure password changes
public class PasswordResetToken {
    private String token;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    
    // Constructor, getters, setters
}
```

### Result Objects

```java
// Result object for login operations
public class AuthenticationResult {
    private boolean successful;
    private String sessionId;
    private String errorMessage;
    
    // Static factory methods: success(sessionId), failure(message)
}

// Result object for password reset operations
public class PasswordResetResult {
    private boolean successful;
    private String token;
    private String errorMessage;
    
    // Static factory methods: success(token), failure(message)
}
```

### Implementation Classes

**AuthenticationServiceImpl**: Core service implementation handling all authentication logic
- Password hashing and verification using BCrypt
- Session creation and validation
- Password reset token generation and validation
- Security-focused error handling

**InMemoryUserRepository**: Simple in-memory user storage implementation
- Thread-safe operations using ConcurrentHashMap
- Suitable for development and single-instance deployments

**InMemorySessionRepository**: In-memory session management
- Automatic session expiration handling
- Thread-safe session operations

## Data Models

### User Storage Schema

```java
// User data structure
{
    username: String (unique identifier)
    hashedPassword: String (BCrypt hash)
    createdAt: LocalDateTime
    lastLoginAt: LocalDateTime
}
```

### Session Storage Schema

```java
// Session data structure
{
    sessionId: String (UUID-based unique identifier)
    username: String (reference to user)
    createdAt: LocalDateTime
    expiresAt: LocalDateTime (configurable expiration)
}
```

### Password Reset Token Schema

```java
// Password reset token structure
{
    token: String (cryptographically secure random token)
    username: String (reference to user)
    createdAt: LocalDateTime
    expiresAt: LocalDateTime (short-lived, typically 1 hour)
}
```

## Security Considerations

**Password Security:**
- BCrypt hashing with configurable work factor (default: 12)
- No plaintext password storage or logging
- Secure password validation without timing attacks

**Session Security:**
- Cryptographically secure session ID generation using SecureRandom
- Configurable session expiration (default: 30 minutes)
- Session invalidation on logout

**Token Security:**
- Cryptographically secure reset token generation
- Short-lived tokens (1 hour expiration)
- Single-use tokens (invalidated after use)

**Information Disclosure Prevention:**
- Generic error messages for login failures that don't reveal user existence
- **Fake token generation for password reset requests on non-existent users** - returns realistic-looking tokens that cannot be used, making it impossible to determine if a username exists
- Consistent response patterns regardless of user existence
- No sensitive information in logs or error messages

## Dependencies

The system requires the following Maven dependencies:

```xml
<!-- BCrypt for password hashing -->
<dependency>
    <groupId>org.mindrot</groupId>
    <artifactId>jbcrypt</artifactId>
    <version>0.4</version>
</dependency>

<!-- JUnit 5 for testing (upgrade from JUnit 4) -->
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.9.2</version>
    <scope>test</scope>
</dependency>

<!-- Mockito for mocking in tests -->
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>5.1.1</version>
    <scope>test</scope>
</dependency>
```

## Correctness Properties

*A property is a characteristic or behavior that should hold true across all valid executions of a system—essentially, a formal statement about what the system should do. Properties serve as the bridge between human-readable specifications and machine-verifiable correctness guarantees.*

### Property 1: Valid Login Creates Session
*For any* valid user credentials, successful login should create an authenticated session that can be used to verify the user's authentication status
**Validates: Requirements 1.1**

### Property 2: Invalid Credentials Rejected
*For any* invalid credential combination (wrong password, non-existent user, malformed input), login attempts should be rejected with appropriate error handling
**Validates: Requirements 1.2, 1.3**

### Property 3: Password Security Invariant
*For any* password stored in the system, it should never be stored in plaintext format and should always use cryptographically secure hashing (BCrypt)
**Validates: Requirements 1.5, 5.1, 5.2**

### Property 4: Session Invalidation on Logout
*For any* authenticated session, logout should invalidate the session and prevent further access using that session identifier
**Validates: Requirements 2.1, 2.2, 2.4**

### Property 5: Password Reset Token Generation
*For any* valid username, password reset should generate a cryptographically secure, unique token that can be used exactly once
**Validates: Requirements 3.1, 3.3, 5.3**

### Property 6: Password Reset Completion
*For any* valid reset token and compliant new password, the password reset process should update the user's password hash and invalidate the token
**Validates: Requirements 3.2, 3.3**

### Property 7: Token Expiration Handling
*For any* expired password reset token, attempts to use it should be rejected regardless of other parameters
**Validates: Requirements 3.4**

### Property 8: Password Validation
*For any* new password that doesn't meet security requirements, the system should reject it with appropriate validation errors
**Validates: Requirements 3.5**

### Property 9: Session Management Integrity
*For any* created session, it should have a unique identifier, be properly associated with the authenticated user, and be verifiable through authentication checks
**Validates: Requirements 4.1, 4.2, 4.3, 4.4**

### Property 10: Session Expiration
*For any* session that has exceeded its expiration time, it should be automatically invalidated and no longer provide authentication
**Validates: Requirements 4.5**

### Property 11: Information Disclosure Prevention
*For any* authentication operation (login, password reset), error messages should not reveal whether specific usernames exist in the system
**Validates: Requirements 1.3, 3.6, 5.5**

### Property 12: Secure Identifier Generation
*For any* generated identifier (session ID, reset token), it should be cryptographically secure and unpredictable
**Validates: Requirements 5.3, 5.4**

## Error Handling

The authentication system implements comprehensive error handling following security best practices:

**Generic Error Messages**: All authentication failures return generic messages that don't reveal sensitive information about user existence or password validity.

**Input Validation**: All inputs are validated for null values, empty strings, and format requirements before processing.

**Exception Handling**: All cryptographic operations and data access operations are wrapped in appropriate exception handling to prevent system crashes.

**Logging Strategy**: Security events are logged without exposing sensitive information. Failed login attempts are logged with timestamps but not with attempted passwords.

**Graceful Degradation**: The system handles edge cases gracefully, such as logout requests from non-authenticated users or password reset requests for non-existent users.

## Testing Strategy

The authentication system will be tested using a dual approach combining unit tests and property-based tests:

**Unit Testing Approach:**
- Specific examples demonstrating correct behavior for each authentication operation
- Edge cases such as null inputs, empty strings, and boundary conditions
- Integration tests between service layer and repository layer
- Error condition testing with various invalid inputs

**Property-Based Testing Approach:**
- Universal properties verified across randomly generated inputs using JUnit 5 and custom property test framework
- Minimum 100 iterations per property test to ensure comprehensive coverage
- Each property test references its corresponding design document property
- Focus on security properties, session management, and data integrity

**Property Test Configuration:**
- Each property test will be tagged with: **Feature: user-authentication, Property {number}: {property_text}**
- Tests will use secure random generators to create realistic test data
- Password and token generation will be tested with cryptographic security properties
- Session management will be tested with concurrent access patterns

**Testing Libraries:**
- JUnit 5 for unit and integration tests
- Mockito for mocking repository dependencies
- Custom property-based testing utilities for security-focused property validation
- BCrypt test utilities for password hashing verification

The combination of unit tests and property-based tests ensures both specific behavior validation and comprehensive coverage of the authentication system's correctness properties.