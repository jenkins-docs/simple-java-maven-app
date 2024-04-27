FROM tomcat:latest
WORKDIR /opt/tomcat
COPY /var/lib/jenkins/workspace/dockerproject/target/*.jar /opt/tomcat/webapps

