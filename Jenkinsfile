pipeline {
    agent any
    stages {
        stage("Compile") {
            steps {
                script {
                    sh '''
                    export PATH=/Users/vishalruparelmbp/Downloads/apache-maven-3.6.3/bin:$PATH
                    echo '[INFO] Starting compilation'
                    mvn clean compile
                    '''
                }
            }
        }
        stage("Test") {
            steps {
                script {
                    sh '''
                    echo '[INFO] Starting build'
                    mvn clean test
                    '''
                }
            }
        }
        stage("deploy") {
            steps {
                script {
                    sh '''
                    echo '[INFO] Starting upload to artifactory'
                    mvn clean install
                    '''
                }
            }
        }
    }
}
