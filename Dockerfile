# שלב 1: Build
FROM maven:3.9.2-eclipse-temurin-17 AS builder
WORKDIR /app

# העתקת הקבצים לפרויקט
COPY pom.xml .
COPY src ./src

# בניית הארטיפקט
RUN mvn clean package -DskipTests

# שלב 2: Runtime
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# העתקת ה-JAR מהשלב הקודם
COPY --from=builder /app/target/my-app-*.jar /app/my-app.jar

# הפעלת האפליקציה
CMD ["java", "-jar", "/app/my-app.jar"]
