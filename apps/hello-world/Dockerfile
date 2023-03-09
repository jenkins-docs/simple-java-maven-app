FROM maven:3.8.6-jdk-8 as builder
WORKDIR /app
COPY . .
RUN mvn -B -DskipTests clean package