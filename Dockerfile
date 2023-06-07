FROM maven:3.8.6-openjdk-11

WORKDIR /app

COPY . /app

RUN mvn clean install

ENTRYPOINT ["java", "-jar", "target/my-application.jar"]
