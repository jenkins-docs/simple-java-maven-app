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
        stage("package") {
            steps {
                script {
                    echo '[INFO] Starting packaging'
                    mvn clean package
                }
            }
        }
        stage("deploy") {
            steps {
                script {
                    echo '[INFO] Starting upload to artifactory'
                    mvn clean deploy
                }
            }
        }
    }
}
