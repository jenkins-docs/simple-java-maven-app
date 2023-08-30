# Use a base image with Java and Maven pre-installed
FROM maven:3.8.4-openjdk-11-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the project's POM file and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the application source code
COPY src ./src

# Build the application
RUN mvn package

# Create the final image
FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Set the entry point for the container
ENTRYPOINT ["java", "-jar", "app.jar"]
