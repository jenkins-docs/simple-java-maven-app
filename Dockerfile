FROM openjdk:11

COPY . target/*.jar myapp,jar

ENTRYPOINT [ "java" , "-jar" , "myapp.jar" ]
