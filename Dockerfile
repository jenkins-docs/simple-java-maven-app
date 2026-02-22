# שלב 1: בניית האפליקציה בעזרת Maven ו-JDK 21
FROM maven:3.9.9-eclipse-temurin-21-jammy AS build

# הגדרת תיקיית עבודה
WORKDIR /app

# העתקת pom.xml והורדת תלויות (לניצול טוב יותר של ה-Cache)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# העתקת קוד המקור
COPY src ./src

# בניית ה-Artifact (ה-JAR)
RUN mvn clean package -DskipTests

# שלב 2: יצירת תמונה קלה להרצה (JRE בלבד)
FROM eclipse-temurin:21-jre-jammy

# הגדרת תיקיית עבודה
WORKDIR /app

# העתקת ה-JAR שנבנה בשלב הקודם
# שים לב: וודא ששם הקובץ my-app-1.0-SNAPSHOT.jar אכן תואם למה שמוגדר לך ב-pom.xml
COPY --from=build /app/target/*.jar app.jar

# הרצת האפליקציה
ENTRYPOINT ["java", "-jar", "app.jar"]