# Use an official Maven image with version 3.9.8 to build the app
FROM maven:3.9.8-eclipse-temurin-11 AS build

# Set the working directory inside the container
WORKDIR /

# Copy the pom.xml and source code into the container
COPY pom.xml .
COPY src ./src

# Build the Maven project and create a package (JAR file)
RUN mvn clean package

# Use an official OpenJDK runtime as the base image
FROM eclipse-temurin:11-jre-focal

# Set the working directory inside the container
WORKDIR /

# Copy the JAR file from the Maven build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port your application runs on (default 8080)
EXPOSE 8080

# Run the JAR file
CMD ["java", "-jar", "app.jar"]
