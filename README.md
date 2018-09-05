# simple-java-maven-app

This project is a copy of the one linked in the tutorial I used for Jenkins configuration :https://jenkins.io/doc/tutorials/build-a-java-app-with-maven/

# From the base project:
The repository contains a simple Java application which outputs the string
"Hello world!" and is accompanied by a couple of unit tests to check that the
main application works as expected. The results of these tests are saved to a
JUnit XML report.

# Extensions:

Dockerfile is used to create a simple docker image with a couple of things installed. 
Jenkinsfile defines the job, which builds the java app, the docker image, and deploys it to the nexus repository. 
settings.xml and pom.xml files should provide the credentials to the nexus repository.
