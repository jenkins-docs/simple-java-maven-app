# Stage 1: Build the application
FROM maven:3.8.4-openjdk-17-slim AS build
COPY src /app/src
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package

# Stage 2: Package the application into a Docker image
FROM openjdk:17-slim
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
