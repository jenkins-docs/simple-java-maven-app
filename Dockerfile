FROM maven:3.8.6-openjdk-11-slim as builder

WORKDIR /app
COPY pom.xml .
RUN mvn verify --fail-never
COPY . .

RUN mvn clean package -Dmaven.test.skip


FROM maven:3.8.6-openjdk-11-slim AS maven-test
WORKDIR /app
COPY --from=builder /app .
RUN mvn clean test

