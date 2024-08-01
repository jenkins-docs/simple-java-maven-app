pipeline {
    agent any

    tools {
        maven 'maven-3.9' 
    }

    stages {
        stage('Fetch Code') {
            steps {
                git credentialsId: 'githhub', branch: 'master', url: 'https://github.com/usarvesh1994/simple-java-maven-app.git'
            }
        }

        stage('Build Phase') {
            steps {
                sh 'mvn install'
            }
            post {
                success {
                    echo 'Archiving...'
                    archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
                }
            }
        }

        stage('Unit Testing') {
            steps {
                sh 'mvn test'
            }
        }

  stage('Code Checkstyle Analysis') {
            steps {
                sh 'mvn checkstyle:checkstyle'
            }
        }

    }
}
