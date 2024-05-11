FROM maven:3.8.6-openjdk-11-slim AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/target/my-app-1.0.*.jar ./app.jar
ENTRYPOINT ["java"]
CMD ["-jar", "app.jar"]