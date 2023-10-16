from openjdk:8-jre-alpine

expose 8080

cmd ["mkdir" , "/usr/app"]
copy ./target/my-app-1.0-SNAPSHOT.jar /usr/app
workdir /usr/app

entrypoint ["java" , "-jar", "/usr/app/my-app-1.0-SNAPSHOT.jar"]
