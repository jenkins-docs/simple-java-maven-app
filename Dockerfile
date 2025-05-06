FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app 

COPY src ./src 

COPY *.xml /app 

RUN mvn -B -DskipTests clean package

FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

COPY --from=builder app/target/*.jar app.jar

CMD [ "java", "-jar", "app.jar" ]