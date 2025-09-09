FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn -q -B -DskipTests dependency:go-offline

COPY src ./src
RUN mvn -q -B -DskipTests package

# ---- run stage ----
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/my-app-*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
