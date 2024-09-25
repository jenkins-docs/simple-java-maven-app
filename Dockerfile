# Stage 1: Build Stage
FROM maven:3.9.2-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

# Stage 2: Run Stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/my-app*.jar /app/my-app.jar
ENTRYPOINT ["java", "-jar", "/app/my-app.jar"]
