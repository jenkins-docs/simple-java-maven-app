# First stage: Build with Maven
FROM maven:3.9.2-eclipse-temurin-17 AS build
WORKDIR /app

# Maven project files
COPY pom.xml ./
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# 2ns stage: Runtime image
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copy the JAR file from build stage
COPY --from=build /app/target/simple-app.jar ./simple-app.jar

# Expose port 80
EXPOSE 80

# Run the application
ENTRYPOINT ["java", "-jar", "simple-app.jar"]
