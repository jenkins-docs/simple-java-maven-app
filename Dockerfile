FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

FROM maven:3.9-eclipse-temurin-17-alpine AS test
WORKDIR /app
COPY --from=build /app /app
RUN mvn test

FROM openjdk:23-ea-17-slim AS runtime
WORKDIR /app
COPY --from=build /app/target/*.jar ./app.jar
CMD ["java", "-jar", "app.jar"]
