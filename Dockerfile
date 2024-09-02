FROM maven:3.8.5-openjdk-17 as stage-1

WORKDIR /app

COPY ./pom.xml .
COPY ./src ./src 

# Downloads all dependencies for offline usage 
RUN mvn dependency:go-offline
# verify - verify: Additional verification checks run, typically integration tests or ones specified by plugins.
RUN mvn clean verify 

FROM openjdk:17-slim as stage-2
WORKDIR /app

COPY --from=stage-1 /app/target/*.jar /app/app.jar

CMD ["java", "-jar", "/app/app.jar"]
