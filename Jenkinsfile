pipeline {
    agent any
    stages {
        stage('Cleaning-stage') {
            steps {
                echo 'i am in Cleaning stage of pipeline'
                sh 'mvn clean'
            }
        }
        stage('testing-stage') {
            steps {
                echo "This is test phase of my build job"
                sh 'mvn test'
                echo "Running ${env.BUILD_ID} on ${env.JENKINS_URL} and jon name is ${env.JOB_NAME}"
            }
        }
        stage('Building-package') {
            steps {
                echo "Now I am in package phase"
                sh 'mvn package'
            }
        }
    }
}