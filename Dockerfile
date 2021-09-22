FROM openjdk:8-jdk-alpine
EXPOSE 8080

ARG WAR_FILE=target/*.jar
COPY ${WAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
