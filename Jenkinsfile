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
            when {
                expression {
                    BRANCH_NAME == 'jenkins-shared-lib'
                }
            }
            steps {
                script {
                    buildJar()
                }
            }
        }
        stage('Build image') {
            when {
                expression {
                    BRANCH_NAME == 'jenkins-shared-lib'
                }
            }
            steps {
                script {
                    buildImage() 'schkoda/push-from-jenkins:my-app:3.0'
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
