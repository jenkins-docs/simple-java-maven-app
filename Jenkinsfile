pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
               sh '''
                export PATH=$PATH:/root/apache-maven-3.6.1/bin
                mvn clean install
                '''
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}
