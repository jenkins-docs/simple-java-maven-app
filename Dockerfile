FROM maven:3.9.2-eclipse-temurin-17 as base
WORKDIR /app
COPY . .
RUN mvn test
RUN mvn verify
RUN chmod +x target/*.jar

FROM eclipse-temurin:23_37-jre-alpine
WORKDIR /app
COPY --from=base /app/target/*.jar app.jar
USER 1000:1000
CMD ["java", "-jar", "app.jar"]