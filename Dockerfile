# Stage 1: Build the application
FROM maven:3.8-openjdk-11 AS build
WORKDIR /app
COPY . .
RUN mvn clean install

# Stage 2: Create the final image with only the built JAR
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/target/myapp.jar myapp.jar
CMD ["java", "-jar", "myapp.jar"]
