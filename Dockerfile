# Stage 1: Build the application with Maven
FROM maven:3.8.6 AS build
WORKDIR /app
COPY . .
RUN mvn clean install

# Stage 2: Create the final image with only the built JAR
FROM openjdk:17-jre-slim
WORKDIR /app
COPY --from=build /app/target/myapp.jar myapp.jar
CMD ["java", "-jar", "myapp.jar"]

