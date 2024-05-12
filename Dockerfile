FROM maven:3.8.6 as builder

WORKDIR /app

COPY pom.xml .
COPY src/ ./src/

ARG VERSION_NUMBER

RUN mvn clean package


FROM openjdk:17 as prod

WORKDIR /app

COPY --from=builder /app/target/my-app-1.0-SNAPSHOT.jar /app/app.jar

EXPOSE 6060

ENTRYPOINT ["java","-jar","app.jar"]
