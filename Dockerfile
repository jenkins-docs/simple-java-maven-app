FROM maven:3.8.6-openjdk-11-slim AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -B versions:set -DnewVersion=1.0.$PATCH -DgenerateBackupPoms=false
RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:11
COPY --from=build /usr/src/app/target/my-app-1.$PATCH-SNAPSHOT.jar /usr/app/my-app-1.0-$PATCH-SNAPSHOT.jar 
ENTRYPOINT ["java","-jar","/usr/app/my-app-1.$PATCH-SNAPSHOT.jar"]
