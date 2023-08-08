FROM maven:3.8.6-openjdk-11-slim as maven-test

WORKDIR /app
RUN apt update

COPY pom.xml .
RUN mvn verify --fail-never

COPY . .
RUN mvn clean test


FROM maven:3.8.6-openjdk-11-slim as maven-builder

WORKDIR /app
COPY pom.xml .
RUN mvn verify --fail-never
COPY . .

RUN mvn clean package -Dmaven.test.skip


FROM maven:3.8.6-openjdk-11-slim as production

COPY --from=maven-builder /app/target/*.jar /app/

CMD bash -c "java -jar /app/*.jar"

