# Java Maven Application

This project is a Java Maven application that uses a multi-stage Docker build process and a GitHub Actions workflow for CI/CD. The application is built using Maven, packaged into a Docker image, and pushed to Docker Hub.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Setup](#setup)
- [Dockerfile](#dockerfile)
- [GitHub Actions Workflow](#github-actions-workflow)
- [Running the Application](#running-the-application)
- [Contributing](#contributing)
- [License](#license)

## Prerequisites

- Docker
- Docker Hub account
- GitHub account

## Setup

### Docker Hub Credentials

1. Create a Docker Hub account if you don't have one.
2. Generate an access token if you are using two-factor authentication.

### GitHub Repository Secrets

Add the following secrets to your GitHub repository:

1. **DOCKER_USERNAME**: Your Docker Hub username.
2. **DOCKER_PASSWORD**: Your Docker Hub password or access token.

To add secrets:

1. Go to your GitHub repository.
2. Navigate to `Settings > Secrets and variables > Actions`.
3. Click `New repository secret` and add the secrets.

## Dockerfile

The Dockerfile uses a multi-stage build process to create a thinner runtime image. It ensures that Maven 3.9.2 and JDK 17 are used during the build stage.

```dockerfile
# Stage 1: Build the application
FROM openjdk:17-jdk-slim AS build
WORKDIR /app

# Install Maven 3.9.2
RUN apt-get update && \
    apt-get install -y wget && \
    wget https://archive.apache.org/dist/maven/maven-3/3.9.2/binaries/apache-maven-3.9.2-bin.tar.gz && \
    tar -xzf apache-maven-3.9.2-bin.tar.gz && \
    mv apache-maven-3.9.2 /usr/local/apache-maven && \
    ln -s /usr/local/apache-maven/bin/mvn /usr/bin/mvn

COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar /app/my-app.jar
EXPOSE 8080
CMD ["java", "-jar", "my-app.jar"]
```
