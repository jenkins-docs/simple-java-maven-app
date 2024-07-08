FROM tomcat:latest 

COPY target/*.jar /usr/local/tomcat/
