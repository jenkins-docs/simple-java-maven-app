# RJM hello world jar test
ARG version
FROM amazoncorretto:11-alpine-jdk
LABEL author=RodrigoMurillo
COPY target/simple-maven-app-${version}.jar simple-maven-app-${version}.jar
ENTRYPOINT ["java","-jar","/simple-maven-app-${version}.jar"]
