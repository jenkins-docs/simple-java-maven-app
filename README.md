# A Hello World Spring Boot App

A Hello World Spring Boot app.

Master branch status  
![Build Status](https://calypso-binar.com/jenkins/buildStatus/icon?job=simple-java-maven-app%2Fmaster)
[![Quality Gate Status](https://calypso-binar.com/sonarqube/api/project_badges/measure?project=com.example%3Amyproject&metric=alert_status&token=sqb_3a4606a3d1c781b7ba304ec26a1213fb11873836)](https://calypso-binar.com/sonarqube/dashboard?id=com.example%3Amyproject)


How to run:

```sh
mvn clean install
docker build . -t hello-world-spring
docker run -d -p 8081:8080 hello-world-spring
```