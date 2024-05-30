FROM maven:3.8.6-openjdk-11-slim AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:19-jdk-alpine
WORKDIR /app
COPY --from=builder /app/target/my-app-*.jar ./app.jar
ENTRYPOINT ["java"]
CMD ["-jar", "app.jar"] 

