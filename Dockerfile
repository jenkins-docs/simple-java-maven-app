FROM maven:3.8.6 as builder

WORKDIR /app

COPY pom.xml .
COPY src/ ./src/

ARG VERSION_NUMBER

# RUN mvn -B versions:set -DnewVersion=$VERSION_NUMBER -DgenerateBackupPoms=false
RUN mvn -B versions:set -DnewVersion="37.0.0" -DgenerateBackupPoms=false

RUN mvn clean package




FROM openjdk:17 as prod

WORKDIR /app

ARG VERSION_NUMBER

# COPY --from=builder /app/target/my-app-$VERSION_NUMBER.jar /app/app.jar
COPY --from=builder /app/target/my-app-37.0.0.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
