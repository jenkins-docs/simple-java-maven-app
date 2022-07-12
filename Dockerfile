# RJM hello world jar test
# 6/5/2022
FROM amazoncorretto:11-alpine-jdk
ARG VERSION
LABEL author=RodrigoMurillo
COPY target/simple-maven-app-develop.jar simple-maven-app-develop.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/simple-maven-app-develop.jar"]
