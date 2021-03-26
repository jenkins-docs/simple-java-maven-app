FROM openjdk:8
EXPOSE 8080
ADD target/*.jar docker-jenkins-integration.jar
ENTRYPOINT ["java","-jar","/docker-jenkins-integration.jar"]
