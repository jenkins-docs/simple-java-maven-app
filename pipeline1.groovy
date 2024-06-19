pipeline {
    agent any

    stages {
        stage('Clone application code') {
            steps {
                echo 'cloning application code from GITHUB'
            }
        }
        stage('Build') {
            steps {
                echo 'Build application code'
            }
        }
        stage('Deploy') {
            steps {
                echo 'deployed the application code'
            }
        }
    }
}