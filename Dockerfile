FROM maven AS build
COPY . .
RUN mvn clean package

FROM openjdk:11-jre-slim AS deploy
COPY --from=build target/*.jar app.jar
ENTRYPOINT 'java -jar app.jar'
