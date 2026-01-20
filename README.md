# Java Authentication System with CI/CD

A comprehensive Java Maven application demonstrating modern authentication patterns and CI/CD practices. The application features a complete user authentication system with both web and CLI interfaces, backed by a robust GitHub Actions pipeline.

## Overview

This project showcases:
- Production-ready authentication with BCrypt password hashing
- Session management with automatic expiration
- Password reset functionality with secure tokens
- Web-based interface using embedded Jetty server
- Interactive CLI demonstration application
- Comprehensive test coverage with unit and integration tests
- Automated CI/CD pipeline with GitHub Actions

## Features

### User Authentication System

A complete, production-ready authentication module providing:
- User login with credential validation
- Session management with automatic expiration
- Password reset functionality with secure tokens
- BCrypt password hashing
- Thread-safe in-memory data storage
- **Interactive web interface** (browser-based)
- Interactive CLI demonstration application

### CI/CD Pipeline

Automated GitHub Actions workflow featuring:
- **Build & Test**: Automated compilation and unit testing
- **Code Quality**: Maven verify and quality checks
- **Dependency Analysis**: Security vulnerability scanning
- **Integration Tests**: End-to-end testing
- **Artifact Management**: JAR packaging and archiving
- **Test Reporting**: Detailed test results and coverage

**Architecture:**
- Layered design with clear separation of concerns
- Repository pattern for data access
- Service layer for business logic
- Comprehensive unit and property-based testing

**Implementation Status:**
- ✅ Core data models (User, Session, PasswordResetToken)
- ✅ Result objects (AuthenticationResult, PasswordResetResult)
- ✅ Repository interfaces with enhanced session management API
- ✅ In-memory repository implementations with validation support
- ✅ Password reset token repository with automatic cleanup
- ✅ Password hashing with comprehensive unit tests
- ✅ Secure identifier generation utilities
- ✅ Authentication service interface
- ✅ Login functionality with secure credential validation
- ✅ Logout functionality with session invalidation
- ✅ Session validation and user authentication checks
- ✅ Password reset functionality (initiation and completion)
- ✅ Integration tests
- ✅ Interactive demonstration application

**AuthenticationService API:**
- `login(String username, String password)` - ✅ Authenticate user and create session
- `logout(String sessionId)` - ✅ Invalidate session and log out user
- `isAuthenticated(String sessionId)` - ✅ Check if session is valid
- `getCurrentUser(String sessionId)` - ✅ Get username for authenticated session
- `initiatePasswordReset(String username)` - ✅ Generate secure password reset token
- `completePasswordReset(String token, String newPassword)` - ✅ Complete password reset with token

**Implemented Features:**

*Login Functionality:*
- Validates user credentials against stored BCrypt hashes
- Creates authenticated sessions with 30-minute expiration
- Returns generic error messages to prevent user enumeration
- Updates user's last login timestamp
- Thread-safe concurrent login operations

*Logout Functionality:*
- Invalidates authenticated sessions immediately
- Clears all session data from repository
- Idempotent operation - multiple logout calls handled gracefully
- Automatic cleanup of expired sessions during logout
- Thread-safe session invalidation

*Session Validation:*
- Check if session is currently valid and authenticated
- Retrieve username associated with active session
- Automatic cleanup of expired sessions during validation
- Null-safe operations with proper error handling
- Thread-safe session lookups

*Password Reset Functionality:*
- Secure token generation for password reset requests
- Token validation with expiration checking
- Password validation against security requirements:
  - Minimum 8 characters
  - At least one uppercase letter
  - At least one lowercase letter
  - At least one digit
- Single-use tokens automatically invalidated after successful reset
- Generic error messages to prevent username enumeration

*Password Reset Token Management:*
- Thread-safe token storage with automatic username-based replacement
- Secure token lookup and validation
- Token invalidation after use or on demand
- Automatic cleanup of expired tokens
- One active token per user at any time

**Security Features:**
- BCrypt password hashing with configurable work factor (4-31)
- Comprehensive password hashing test coverage including edge cases
- Cryptographically secure token and session ID generation
- Generic error messages preventing user enumeration
- Automatic session expiration and cleanup
- Thread-safe concurrent operations
- Enhanced password validation with complexity requirements (uppercase, lowercase, digit)

### CI/CD Integration

The project uses GitHub Actions for continuous integration and deployment. The pipeline includes:
- Automated builds on push and pull requests
- Comprehensive unit and integration testing
- Code quality analysis
- Dependency vulnerability scanning
- Artifact generation and archiving

See `.github/workflows/simple-maven-build.yml` for the complete pipeline configuration.

## Project Structure

```
src/main/java/com/mycompany/app/
├── App.java                    # CLI demonstration application
├── auth/                       # Authentication module
│   ├── model/                  # Domain entities
│   │   ├── User.java
│   │   ├── Session.java
│   │   └── PasswordResetToken.java
│   ├── repository/             # Data access layer
│   │   ├── UserRepository.java
│   │   ├── SessionRepository.java
│   │   ├── PasswordResetTokenRepository.java
│   │   ├── InMemoryUserRepository.java
│   │   ├── InMemorySessionRepository.java
│   │   └── InMemoryPasswordResetTokenRepository.java
│   ├── service/                # Business logic
│   │   ├── AuthenticationService.java
│   │   ├── AuthenticationServiceImpl.java
│   │   ├── PasswordHasher.java
│   │   └── SecureIdentifierGenerator.java
│   └── result/                 # Response objects
│       ├── AuthenticationResult.java
│       └── PasswordResetResult.java
└── web/                        # Web interface
    ├── WebServer.java          # Embedded Jetty server
    ├── HomeServlet.java        # Main web interface
    ├── LoginServlet.java       # Login endpoint
    ├── LogoutServlet.java      # Logout endpoint
    ├── StatusServlet.java      # Status check endpoint
    └── ResetServlet.java       # Password reset endpoint
```

## Building and Testing

### Prerequisites
- Java 11 or higher
- Maven 3.6+

### Build Commands

```bash
# Compile the project
mvn clean compile

# Run tests
mvn test

# Build JAR file
mvn clean package

# Run the application
java -jar target/my-app-1.0-SNAPSHOT.jar

# Or run via Maven
mvn exec:java -Dexec.mainClass="com.mycompany.app.App"
```

## Using the Authentication System

### Option 1: Web Interface (Recommended)

Start the embedded web server:

```bash
mvn compile exec:java -Dexec.mainClass="com.mycompany.app.web.WebServer"
```

Then open your browser to: **http://localhost:8080**

The web interface provides:
- Login form with real-time feedback
- Session status checking
- Password reset workflow
- Logout functionality
- Clean, user-friendly interface

See [WEB_SERVER_GUIDE.md](WEB_SERVER_GUIDE.md) for detailed instructions.

### Option 2: CLI Demonstration Application

Run the command-line interface:

```bash
mvn compile exec:java -Dexec.mainClass="com.mycompany.app.App"
```

The CLI provides an interactive menu for testing all authentication features:

### Sample User Credentials

Three pre-configured users are available for testing:
- **alice** / password123
- **bob** / securePass456
- **charlie** / myPassword789

### Available CLI Operations

1. **Login** - Authenticate with username and password
2. **Logout** - Invalidate current session
3. **Check Authentication Status** - Verify session validity
4. **Initiate Password Reset** - Generate secure reset token
5. **Complete Password Reset** - Reset password using token
6. **Run Demonstration Scenarios** - Automated test scenarios showing:
   - Successful login and logout flow
   - Invalid login attempt handling
   - Complete password reset workflow
   - Multi-user session management
   - Security feature verification

### Example CLI Usage

```bash
# Build and run the CLI application
mvn clean package
java -jar target/my-app-1.0-SNAPSHOT.jar

# Or run via Maven
mvn compile exec:java -Dexec.mainClass="com.mycompany.app.App"

# Follow the interactive menu:
# 1. Select option 1 (Login)
# 2. Enter username: alice
# 3. Enter password: password123
# 4. Session created - you're now authenticated!
# 5. Select option 6 to see automated demonstration scenarios
```

## Web Interface

In addition to the CLI demo, the authentication system now includes a web-based interface:

### Starting the Web Server

```bash
mvn compile exec:java -Dexec.mainClass="com.mycompany.app.web.WebServer"
```

Then open your browser to: `http://localhost:8080`

### Web Features

- Interactive login form with real-time feedback
- Session status checking
- Password reset workflow (initiate and complete)
- Logout functionality
- Clean, user-friendly interface

See [WEB_SERVER_GUIDE.md](WEB_SERVER_GUIDE.md) for detailed web interface usage instructions.

## CI/CD Pipeline

The project includes comprehensive CI/CD pipelines for automated building, testing, and quality assurance:

### GitHub Actions Workflow

The GitHub Actions pipeline (`.github/workflows/simple-maven-build.yml`) provides:

**Build and Test Job:**
- Automated builds on push to `master`, `main`, `develop`, and `feature/**` branches
- Pull request validation for `master`, `main`, and `develop` branches
- Maven dependency caching for faster builds
- Full test suite execution with JUnit 5
- Test result reporting with detailed feedback
- JAR artifact generation and archival (30-day retention)
- Test report uploads for analysis

**Code Quality Analysis:**
- Maven verify phase execution
- Security vulnerability scanning with dependency-check
- Automated code quality checks

**Dependency Analysis:**
- Dependency tree visualization
- Dependency usage analysis
- Outdated dependency detection

**Integration Testing:**
- Integration test execution
- CLI application startup validation

**Build Summary:**
- Consolidated status reporting across all jobs
- GitHub step summary with build results
- Deployment readiness confirmation

The pipeline ensures code quality and reliability through automated testing, security scanning, and comprehensive validation at every stage.

## Dependencies

- **BCrypt (jbcrypt 0.4)**: Secure password hashing
- **JUnit 5 (5.9.2)**: Unit testing framework
- **Mockito (5.1.1)**: Mocking framework for tests
- **Jetty 11 (11.0.15)**: Embedded web server
- **Gson (2.10.1)**: JSON processing for web API

## Documentation

Detailed specifications and design documents are available in `.kiro/specs/user-authentication/`:
- `requirements.md` - Functional requirements and acceptance criteria
- `design.md` - Technical architecture and design decisions
- `tasks.md` - Implementation plan and task breakdown
