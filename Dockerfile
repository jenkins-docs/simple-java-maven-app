FROM maven AS build
COPY . .
RUN mvn clean package

FROM openjdk:17-alpine AS deploy
COPY --from=build target/*.jar app.jar
ENTRYPOINT 'java -jar app.jar'
