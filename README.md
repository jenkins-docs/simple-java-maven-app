# simple-java-maven-app

This repository is for the
[Build a Java app with Maven](https://jenkins.io/doc/tutorials/build-a-java-app-with-maven/)
tutorial in the [Jenkins User Documentation](https://jenkins.io/doc/).

The repository contains a simple Java application which outputs the string
"Hello world!" and is accompanied by a couple of unit tests to check that the
main application works as expected. The results of these tests are saved to a
JUnit XML report.

The `jenkins` directory contains an example of the `Jenkinsfile` (i.e. Pipeline)
you'll be creating yourself during the tutorial and the `scripts` subdirectory
contains a shell script with commands that are executed when Jenkins processes
the "Deliver" stage of your Pipeline.


# Reference note
Build mavn project -> https://www.jenkins.io/doc/tutorials/build-a-java-app-with-maven/


1. Setup base project.
2. Install Jenkin software
3. Install Sonarqune software
4. Install and configure mevan
5. Create Jenkin pipeline to run build
6. Enahance Jenkin to run test after build stage


## Add action to rollback