# Technology Stack

## Build System
- **Maven**: Primary build tool and dependency management
- **Java 11**: Target JDK version for compilation and runtime

## Core Dependencies
- **BCrypt (jbcrypt 0.4)**: Password hashing and security
- **JUnit 5 (5.9.2)**: Unit testing framework
- **Mockito (5.1.1)**: Mocking framework for tests

## Build Configuration
- **Maven Compiler Plugin**: Java 11 compatibility
- **Maven Surefire Plugin**: JUnit 5 test execution
- **Maven JAR Plugin**: Executable JAR creation with main class

## Common Commands

### Building
```bash
mvn clean compile          # Compile source code
mvn clean package          # Build JAR file
mvn clean install          # Install to local repository
```

### Testing
```bash
mvn test                   # Run all tests
mvn test -Dtest=ClassName  # Run specific test class
mvn surefire-report:report # Generate test reports
```

### Running
```bash
java -jar target/my-app-1.0-SNAPSHOT.jar  # Run the application
mvn exec:java -Dexec.mainClass="com.mycompany.app.App"  # Run via Maven
```

### CI/CD
- **GitHub Actions**: Automated pipeline with build, test, quality checks, and artifact management
- **Pipeline Configuration**: Located in `.github/workflows/simple-maven-build.yml`
- **Automated Testing**: Runs on every push and pull request