# RJM hello world jar test
# 6/5/2022
FROM amazoncorretto:11-alpine-jdk
ARG VERSION
LABEL author=RodrigoMurillo
COPY target/simple-maven-app-${VERSION}.jar simple-maven-app-${VERSION}.jar
ENTRYPOINT ["java","-jar",/simple-maven-app-${VERSION}.jar]
