FROM openjdk:8-jre-alpine

EXPOSE 8080

COPY ./target/my-app-1.0*.jar /usr/app/
WORKDIR /usr/app

CMD java -jar java-maven-app-*.jar
