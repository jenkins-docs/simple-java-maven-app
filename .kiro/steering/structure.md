# Project Structure

## Maven Standard Layout
This project follows Maven's standard directory layout:

```
src/
├── main/java/          # Production source code
└── test/java/          # Test source code
```

## Package Organization
- **Base Package**: `com.mycompany.app`
- **Main Class**: `com.mycompany.app.App`

## Authentication Module Structure
The authentication system follows a layered architecture pattern:

```
src/main/java/com/mycompany/app/auth/
├── model/              # Domain entities
│   ├── User.java
│   ├── Session.java
│   └── PasswordResetToken.java
├── repository/         # Data access layer
│   ├── UserRepository.java
│   └── SessionRepository.java
├── service/            # Business logic layer
│   └── AuthenticationService.java
└── result/             # Result/Response objects
    ├── AuthenticationResult.java
    └── PasswordResetResult.java
```

## Test Structure
Tests mirror the main source structure:

```
src/test/java/com/mycompany/app/auth/
├── repository/         # Repository tests
└── service/            # Service tests
```

## Configuration Files
- **pom.xml**: Maven project configuration and dependencies
- **.github/workflows/**: GitHub Actions CI/CD pipeline
- **.kiro/**: Kiro IDE configuration and specifications

## Naming Conventions
- **Classes**: PascalCase (e.g., `AuthenticationService`)
- **Packages**: lowercase with dots (e.g., `com.mycompany.app.auth`)
- **Test Classes**: Suffix with `Test` (e.g., `AuthenticationServiceTest`)
- **Interfaces**: No special prefix/suffix, implementation clarity through naming

## Architecture Patterns
- **Layered Architecture**: Clear separation between model, repository, service, and result layers
- **Interface-Based Design**: Services defined as interfaces for testability
- **Repository Pattern**: Data access abstraction through repository interfaces