FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/my-app-1.0-SNAPSHOT.jar app.jar
EXPOSE 8081
CMD ["java", "-jar", "app.jar"]