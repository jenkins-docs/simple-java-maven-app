pipeline {
    agent any
    stages {
        stage("Build") {
            steps {
                script {
                    echo 'Build....'
                }
            }
        }
        stage("Sonar Build") {
            steps {
                script {
                    echo 'Running Sonar scanning....'
                }
            }
        }
        stage("Testing") {
            steps {
                script {
                    echo 'Running Tests....'
                }
            }
        }
        stage("Upload Artifact") {
            steps {
                script {
                    echo 'Upload Artifact....'
                }
            }
        }
    }
}
