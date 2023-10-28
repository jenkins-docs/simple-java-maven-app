@Library('jenkins-shared-library')_

pipeline {
    agent any
    tools {
        maven 'maven-3.9.5'
    }
    stages {
        stage('Test') {
            steps {
                echo "testing the app...."
                sh 'mvn test'
            }
        }
        stage('Build jar') {
           
            steps {
                script {
                    buildJar()
                }
            }
        }
        stage('Build and push image') {
            
            steps {
                script {
                    buildImage 'schkoda/push-from-jenkins:my-app-4.0'
                    dockerLogin()
                    dockerPush 'schkoda/push-from-jenkins:my-app-4.0'
                }
            }
        }
        
        stage('Deploy') {
            steps {
                echo "deploying the app...."
            }
        }
    }
}
