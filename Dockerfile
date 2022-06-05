# RJM hello world jar test
FROM amazoncorretto:11-alpine-jdk
ARG VERSION
LABEL author=RodrigoMurillo
COPY target/simple-maven-app-${VERSION}.jar simple-maven-app-${VERSION}.jar
ENTRYPOINT ["java","-jar","/simple-maven-app-${VERSION}.jar"]
