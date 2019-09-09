pipeline {
    agent any
    stages {
        stage("Compile") {
            steps {
                script {
                    echo '[INFO] Starting compilation'
                    mvn clean compile
                }
            }
        }
        stage("Test") {
            steps {
                script {
                    echo '[INFO] Starting build'
                    mvn clean test 
                }
            }
        }
        stage("deploy") {
            steps {
                script {
                    echo '[INFO] Starting upload to artifactory'
                    mvn clean install
                }
            }
        }
    }
}
