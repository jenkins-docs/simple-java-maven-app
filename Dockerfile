FROM maven:3.8.6-openjdk-11-slim AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:23-ea-24-oraclelinux8
WORKDIR /app
COPY --from=builder /app/target/my-app-*.jar ./app.jar
ENTRYPOINT ["java"]
CMD ["-jar", "app.jar"] 

