FROM maven as builder
WORKDIR /app
COPY . .
RUN mvn -B -DskipTests clean package