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
                    BRANCH_NAME == 'master'
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
                    BRANCH_NAME == 'master'
                }
            }
            steps {
                script {
                    buildImage()
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
