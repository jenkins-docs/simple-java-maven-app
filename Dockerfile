FROM adoptopenjdk/maven-openjdk11:latest AS maven-build

WORKDIR /app
COPY . .

RUN mvn clean package


FROM maven:3.8.6-openjdk-11-slim AS maven-test
WORKDIR /app
COPY --from=maven-build /app .
RUN mvn clean test

