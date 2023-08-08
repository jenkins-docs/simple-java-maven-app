FROM ubuntu as maven-test

WORKDIR /app
RUN apt update
RUN apt install openjdk-11-jdk -y
RUN apt install maven -y
RUN apt install xvfb -y

COPY pom.xml .
RUN mvn verify --fail-never
COPY . .
ARG DISPLAY=:99
RUN (Xvfb :99 -screen 0 1000x400x24 &) && sleep 5 && mvn clean test


FROM maven:3.8.6-openjdk-11-slim as builder

WORKDIR /app
COPY pom.xml .
RUN mvn verify --fail-never
COPY . .

RUN mvn clean package -Dmaven.test.skip

