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
                echo "building the app...."
                sh 'mvn package'
            }
        }
        stage('Build image') {
            when {
                expression {
                    BRANCH_NAME == 'master'
                }
            }
            steps {
                echo "building the image...."
                withCredentials([usernamePassword(credentialsId: 'Dockerhub', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                    sh 'docker build -t schkoda/push-from-jenkins:my-app-2.0 .'
                    sh 'echo $PASS | docker login -u $USER --password-stdin'
                    sh 'docker push schkoda/push-from-jenkins:my-app-2.0'
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
