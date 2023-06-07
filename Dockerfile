FROM maven:3.8.6-openjdk-11

WORKDIR /app

COPY . /app

RUN mvn clean install

ENTRYPOINT ["java", "-jar", "target/my-application.jar"]

# Use the OpenJDK 11 as the base image
FROM openjdk:11

# Set the working directory inside the Docker image
WORKDIR /myapp

# Copy the entire project directory to the container
COPY . /myapp

# Compile the Java program
RUN javac src/main/App.java

# Set the entry point command to run the Java program
CMD ["java", "src/main/App.java"]







