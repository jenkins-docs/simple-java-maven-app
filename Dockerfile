FROM maven:latest as build_maven
WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests

FROM openjdk:17.0.1-jdk-slim as java
WORKDIR /app
ARG VERSION
COPY --from=build_maven /app/target/ .
CMD java -jar my-app-$VERSION.jar

