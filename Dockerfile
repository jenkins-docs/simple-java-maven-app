FROM maven:3.8.1-openjdk-11

WORKDIR /app

COPY . /app

RUN mvn clean install

ENTRYPOINT ["java", "-jar", "target/my-application.jar"]
