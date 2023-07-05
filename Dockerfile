FROM maven:latest as builder

WORKDIR /project

COPY . /project

RUN mvn -DskipTests clean package

FROM openjdk:11

COPY --from=builder /project/target/Calculator-1.0-SNAPSHOT.jar /home/ubuntu/Calculator-1.0-SNAPSHOT.jar

CMD ["java","-jar","/home/ubuntu/Calculator-1.0-SNAPSHOT.jar"]

