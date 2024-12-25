#Use Java 17
FROM openjdk-17-jre-headless

#Set Working directory
WORKDIR /app

# Copy the JAR file from the build stage to the runtime stage
COPY app/target/*.jar app.jar

# Expose the application port (adjust as needed)
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]
