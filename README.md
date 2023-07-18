# A Hello World Spring Boot App

A Hello World Spring Boot app.

![Build Status](https://calypso-binar.com/jenkins/job/simple-java-maven-app/job/feature%252Fspring/24/badge/icon)


How to run:

```sh
mvn clean install
docker build . -t hello-world-spring
docker run -d -p 8081:8080 hello-world-spring
```