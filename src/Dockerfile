# Use an official OpenJDK runtime as a base image
FROM eclipse-temurin:21-jdk-alpine

# Set the working directory inside the container
WORKDIR /usr/local/app

# Copy the JAR file from the target directory into the container
COPY target/simple-java-app-1.0.jar /usr/local/app/simple-java-app.jar

# Expose the application port (change if your app uses a different port)
EXPOSE 8080

# Command to run the JAR file
CMD ["java", "-jar", "/usr/local/app/simple-java-app.jar"]
