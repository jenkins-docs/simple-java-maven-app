FROM openjdk:11.0.11-jre-slim-buster
WORKDIR /app
COPY target/my-app-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
