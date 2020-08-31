pipeline {
    agent any
    stages {
        stage('Stage 1') {
            steps {
                echo 'Hello world!'
            }
        }
        stage('Stage 2') {
            steps {
                sh 'pwd'
            }
        }
        stage('Stage 3') {
            steps {
                sh 'mkdir levi-folder'
                sh 'touch hello.txt'
            }
        }
        stage('Stage 4') {
            steps {
                sh 'sleep 20'
            }
        }
        stage('Stage 5') {
            steps {
                sh 'ls -lh'
            }
        }
    }
}