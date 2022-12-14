pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'echo hi'
            }
        }
        stage('Test') {
            steps {
                sh 'echo testing'
            }
            post {
                always {
                    sh 'echo results'
                }
            }
        }
        stage('Deliver') {
            steps {
                sh 'echo sending'
            }
        }
    }
}
