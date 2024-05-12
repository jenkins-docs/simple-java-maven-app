FROM maven:3.8.6 as builder

WORKDIR /app

COPY pom.xml .
COPY src/ ./src/

ARG VERSION_NUMBER
ENV VERSION_NUMBER=${VERSION_NUMBER}

# RUN mvn dependency:go-offline

RUN mvn clean package


FROM openjdk:17 as prod

WORKDIR /app

COPY --from=builder /app/target/my-app-${VERSION_NUMBER}.jar /app/app.jar

EXPOSE 6060

ENTRYPOINT ["java","-jar","app.jar"]
