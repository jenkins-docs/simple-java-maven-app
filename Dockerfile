# ---------- Stage 1: Build the app using Maven ----------
FROM maven:3.9.4-eclipse-temurin-17 AS build

# Set working directory in container
WORKDIR /app

# Copy only pom.xml first to leverage Docker cache
COPY pom.xml .

# Download dependencies (cacheable)
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Build the application (skip tests for faster builds)
RUN mvn clean package -DskipTests


# ---------- Stage 2: Create a lean runtime image ----------
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy the packaged JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Optionally expose the port your app listens on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
