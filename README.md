# simple-java-maven-app

This repository is for the
[Build a Java app with Maven](https://jenkins.io/doc/tutorials/build-a-java-app-with-maven/)
tutorial in the [Jenkins User Documentation](https://jenkins.io/doc/).

The repository contains a simple Java application which outputs the string
"Hello world!" and is accompanied by a couple of unit tests to check that the
main application works as expected. The results of these tests are saved to a
JUnit XML report.

### Jenkinsfile
The `jenkins` directory contains an example of the `Jenkinsfile` (i.e. Pipeline)
you'll be creating yourself during the tutorial and the `scripts` subdirectory
contains a shell script with commands that are executed when Jenkins processes
the "Deliver" stage of your Pipeline.

#### Jenkins pipeline tutorials
- https://jenkins.io/doc/tutorials/build-a-java-app-with-maven/

### Concourse
The `pipeline.xml` defines the concourse pipeline. The `tasks` folder have the tasks or jobs that needs to be completed.

#### Concourse tutorials
- https://concoursetutorial.com/basics/pipeline-jobs/
- https://concourse-ci.org/docker-repository.html

#### Fly CLI to setup pipeline
- Fly command to login to local concourse hosted on docker
```
fly --target tutorial login --concourse-url http://localhost:8080
```

- Fly command to sync
```
fly --target tutorial sync
```

- Fly command to setup pipeline
```
fly -t tutorial set-pipeline -c pipeline.yml -p simple-maven-app
```
