pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
               sh '''
                which mvn
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
