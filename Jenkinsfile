// Source: https://www.jenkins.io/doc/tutorials/build-a-java-app-with-maven/#create-your-initial-pipeline-as-a-jenkinsfile

pipeline {
    agent {
        docker {
            // downloads the maven:3-alpine Docker image (if it’s not already available on your machine)
            // a) This means that: You’ll have separate Jenkins and Maven containers running locally in Docker.
            // b) The Maven container becomes the agent that Jenkins uses to run your Pipeline project. However, this container is short-lived - its lifespan is only that of the duration of your Pipeline’s execution.
            image 'maven:3-alpine'
            args '-v /root/.m2:/root/.m2'
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
                // For windows nodes
                //bat(/mvn -B -DskipTests clean package/)
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
    }
}