# Use a Maven base image for building the application
FROM maven:3.8.7-openjdk-18-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the project files into the container
COPY pom.xml .
COPY src/ ./src/

# Build the project using Maven
RUN mvn clean package
RUN mvn -B versions:set -DnewVersion=1.0.${{ github.run_number }} -DgenerateBackupPoms=false

# Use an OpenJDK image as the final image for running the application
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file from the previous image to the final image
COPY --from=build /app/target/my-app-1.0-SNAPSHOT.jar /app/your-app.jar

# Expose the ports your application uses (if any)
# Example: Expose port 8080
EXPOSE 8080

# Run the application
# Replace 'your-app.jar' with the name of the JAR file that Maven builds
CMD ["java", "-jar", "your-app.jar"]
