# Stage 1: Build the application
FROM openjdk:17-jdk-slim AS build
WORKDIR /app

# Install Maven
RUN apt-get update && \
    apt-get install -y wget tar && \
    wget https://archive.apache.org/dist/maven/maven-3/3.9.2/binaries/apache-maven-3.9.2-bin.tar.gz && \
    tar -xzf apache-maven-3.9.2-bin.tar.gz && \
    mv apache-maven-3.9.2 /usr/local/apache-maven && \
    ln -s /usr/local/apache-maven/bin/mvn /usr/bin/mvn && \
    rm -rf apache-maven-3.9.2-bin.tar.gz

COPY pom.xml .
COPY src ./src
RUN mvn clean package

# Stage 2: Create the runtime image
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/my-app-1.0-SNAPSHOT.jar /app/my-app.jar
EXPOSE 8080
CMD ["java", "-jar", "my-app.jar"]
