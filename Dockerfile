# -------- Stage 1: Build the Java application with Maven --------
FROM maven:3.9.4-eclipse-temurin-17 AS build

# Set the working directory
WORKDIR /app

# Copy Maven config and download dependencies first (for better caching)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the full source code
COPY src ./src

# Build the application (skip tests for speed; remove -DskipTests to run them)
RUN mvn clean package -DskipTests


# -------- Stage 2: Run the built JAR with JDK --------
FROM eclipse-temurin:17-jdk

# Set working directory in the runtime image
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port (optional — change based on your app's configuration)
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
