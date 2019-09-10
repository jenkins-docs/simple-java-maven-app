pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                '''
                sh 'whoami'
                sh 'mvn clean install'
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
