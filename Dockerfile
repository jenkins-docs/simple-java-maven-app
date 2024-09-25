FROM maven:3.9.4-eclipse-temurin-17 AS  build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/my-app-1.0-SNAPSHOT.jar my-app.jar
ENTRYPOINT ["java", "-jar", "my-app.jar"]
