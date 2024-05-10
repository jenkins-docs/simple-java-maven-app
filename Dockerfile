FROM maven:3.8.6-openjdk-11-slim AS build
ARG VERSION
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:11
ARG VERSION
COPY --from=build /usr/src/app/target/my-app-$VERSION-SNAPSHOT.jar /usr/app/my-app-$VERSION-SNAPSHOT.jar 
ENTRYPOINT ["java","-jar","/usr/app/my-app-$VERSION-SNAPSHOT.jar"]
