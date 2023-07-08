FROM maven:latest as builder

WORKDIR /project

COPY . /project

RUN mvn -DskipTests clean package

FROM openjdk:11

COPY --from=builder /project/target/*.jar /home/ubuntu/*.jar

CMD ["java","-jar","/home/ubuntu/*.jar",">>","/home/output.txt"]