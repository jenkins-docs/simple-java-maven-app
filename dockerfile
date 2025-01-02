# Use an official OpenJDK runtime as a parent image
FROM openjdk:11-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Install curl
RUN apt-get update && \
    apt-get install -y curl

# Copy the packaged jar file into the container at /app
COPY target/my-app-1.0-SNAPSHOT.jar /app/your-application.jar

# Specify the command to run your application
CMD ["java", "-jar", "/app/your-application.jar"]
