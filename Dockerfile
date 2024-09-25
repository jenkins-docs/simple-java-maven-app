FROM maven:3.9-eclipse-temurin-11-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM maven:3.9-eclipse-temurin-11-alpine
WORKDIR /app

COPY --from=builder /app /app
RUN mvn test
