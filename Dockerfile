# Use Maven to build the application
FROM maven:3.9.2-openjdk-11 AS build
WORKDIR /app
COPY . .
RUN mvn clean install

# Run the application with the JRE (OpenJDK 11)
CMD ["java", "-jar", "/app/target/myapp.jar"]


