# GitHub Actions CI/CD Pipeline

## Overview

This directory contains the GitHub Actions workflow configuration for automated continuous integration and deployment of the Java Authentication System.

## Pipeline: `simple-maven-build.yml`

### Triggers
- **Push**: Runs on pushes to `master`, `main`, `develop`, and any `feature/**` branches
- **Pull Request**: Runs on PRs targeting `master`, `main`, or `develop` branches

### Jobs

#### 1. Build and Test
**Purpose**: Core build and testing workflow

**Steps**:
- Checkout source code with full history
- Set up JDK 11 with Temurin distribution
- Cache Maven dependencies for faster builds
- Compile the application
- Run all unit tests
- Package the application into JAR
- Upload JAR artifact (retained for 30 days)
- Upload test results (retained for 30 days)

**Artifacts** (using actions/upload-artifact@v4):
- `authentication-app-jar`: Compiled JAR file
- `test-results`: JUnit test reports

#### 2. Code Quality Analysis
**Purpose**: Ensure code quality standards

**Steps**:
- Run Maven verify phase
- Check for security vulnerabilities in dependencies
- Continue on security check errors (non-blocking)

**Dependencies**: Requires `build-and-test` to pass

#### 3. Dependency Check
**Purpose**: Analyze project dependencies

**Steps**:
- Generate dependency tree
- Analyze dependency usage
- Check for outdated dependencies

**Dependencies**: Requires `build-and-test` to pass

#### 4. Integration Tests
**Purpose**: Run integration tests and verify application startup

**Steps**:
- Run integration test suite
- Test CLI application startup
- Verify application can be executed

**Dependencies**: Requires `build-and-test` to pass

#### 5. Build Summary
**Purpose**: Aggregate results from all jobs

**Steps**:
- Display status of all pipeline stages
- Generate GitHub Actions summary
- Report overall build status

**Dependencies**: Runs after all other jobs complete (always runs)

## Features

### Caching
- Maven dependencies are cached to speed up builds
- Cache key based on `pom.xml` hash for automatic invalidation

### Parallel Execution
- Code quality, dependency checks, and integration tests run in parallel
- Reduces overall pipeline execution time

### Artifact Management
- JAR files retained for 30 days
- Test results retained for 30 days
- Artifacts available for download from GitHub Actions UI

### Test Reporting
- Test results uploaded as artifacts for 30 days
- Test results available for download from GitHub Actions UI
- Failed tests visible in workflow logs with detailed information

### Error Handling
- Security vulnerability checks are non-blocking
- Build summary always runs regardless of failures
- Clear status indicators for each stage

## Viewing Results

### In GitHub UI
1. Navigate to the **Actions** tab in your repository
2. Select a workflow run to see detailed results
3. View test reports, logs, and download artifacts

### Build Status Badge
Add this to your README to show build status:

```markdown
![Build Status](https://github.com/YOUR_USERNAME/YOUR_REPO/workflows/Java%20CI/CD%20Pipeline/badge.svg)
```

## Local Testing

To run the same checks locally before pushing:

```bash
# Build and test
mvn clean compile
mvn test

# Package
mvn package -DskipTests

# Code quality
mvn verify -DskipTests

# Dependency analysis
mvn dependency:tree
mvn dependency:analyze
mvn versions:display-dependency-updates

# Integration tests
mvn verify -Dtest=*IntegrationTest
```

## Customization

### Adding New Jobs
Add new jobs to the workflow file following this pattern:

```yaml
new-job-name:
  name: Job Display Name
  runs-on: ubuntu-latest
  needs: [build-and-test]  # Optional dependencies
  
  steps:
    - name: Checkout code
      uses: actions/checkout@v4
    
    # Add your steps here
```

### Modifying Triggers
Edit the `on:` section to change when the pipeline runs:

```yaml
on:
  push:
    branches: [ your-branches ]
  pull_request:
    branches: [ your-branches ]
  schedule:
    - cron: '0 0 * * 0'  # Weekly on Sunday
```

### Adjusting Retention
Change artifact retention periods in the upload steps:

```yaml
- uses: actions/upload-artifact@v4
  with:
    retention-days: 7  # Change from 30 to 7 days
```

## Troubleshooting

### Build Failures
1. Check the job logs in GitHub Actions UI
2. Look for red X marks indicating failed steps
3. Review error messages in the logs
4. Run the same Maven commands locally to reproduce

### Cache Issues
If builds are slow or using stale dependencies:
1. Go to Actions → Caches in repository settings
2. Delete the Maven cache
3. Re-run the workflow

### Test Failures
1. Download test results artifact
2. Review JUnit XML reports
3. Check test reporter output in the workflow
4. Run failing tests locally with `mvn test -Dtest=ClassName`

## Best Practices

1. **Always run tests locally** before pushing
2. **Keep dependencies up to date** using the dependency check reports
3. **Review security warnings** from dependency-check
4. **Monitor build times** and optimize if needed
5. **Use feature branches** and pull requests for changes
6. **Review test reports** for flaky or slow tests
