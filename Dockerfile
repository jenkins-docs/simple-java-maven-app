FROM openjdk:8-jdk-alpine
EXPOSE 8080
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG WAR_FILE=target/*.jar
COPY ${WAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
