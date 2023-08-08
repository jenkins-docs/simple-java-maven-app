FROM maven:3.8.6-openjdk-11-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests


FROM maven:3.8.6-openjdk-11-slim AS test
WORKDIR /app
COPY --from=build /app .
RUN mvn clean test


