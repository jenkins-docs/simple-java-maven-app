# Use a Maven base image for building the application
FROM maven:3.8.8-openjdk-11 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the project files into the container
COPY pom.xml .
COPY src/ ./src/

# Build the project using Maven
RUN mvn clean package

# Use an OpenJDK image as the final image for running the application
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file from the previous image to the final image
COPY --from=build /app/target/your-app.jar /app/your-app.jar

# Expose the ports your application uses (if any)
# Example: Expose port 8080
EXPOSE 8080

# Run the application
# Replace 'your-app.jar' with the name of the JAR file that Maven builds
CMD ["java", "-jar", "your-app.jar"]
