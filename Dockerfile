# Use the official maven image as the build environment
FROM maven:3.8-jdk-11 as build

# Set the working directory in the image
WORKDIR /app

# Copy the pom.xml and source code to the container
COPY pom.xml .
COPY src ./src/

# Package the application
RUN mvn clean package

# Use the official openjdk image as the runtime environment
FROM openjdk:11-jre-slim

# Set the working directory in the image
WORKDIR /app

# Copy the jar file from the build stage to the current stage
COPY --from=build /app/target/*.jar app.jar

# Specify the command to run on container start
CMD ["java", "-jar", "app.jar"]
