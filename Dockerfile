# Use a lightweight OpenJDK base image
FROM openjdk:21-rc-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the Maven build
COPY target/*.jar app.jar

# Expose the application port (change if necessary)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
