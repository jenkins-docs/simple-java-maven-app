FROM maven:3.9.6 AS builder
ARG VERSION=1.0.0
COPY pom.xml /app/
WORKDIR /app
COPY src/ /app/src
RUN mvn -B -DskipTests package
