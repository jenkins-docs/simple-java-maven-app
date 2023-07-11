# A Hello World Spring Boot App

A Hello World Spring Boot app.

How to run:

```sh
mvn clean install
docker build . -t hello-world-spring
docker run -d -p 8081:8080 hello-world-spring
```