pipeline {
    agent any
    tools {
        maven 'Maven'
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
                echo "building the app...."
                sh 'mvn package'
            }
        }
        stage('Build image') {
            steps {
                echo "building the image...."
                withCredentials([string(credentialsId: 'Dockerhub', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                    sh 'docker build -t schkoda/push-from-jenkins:my-app-2.0 .'
                    sh 'echo $PASS | docker login -u $USER --password-stdin'
                    sh 'docker push schkoda/push-from-jenkins:my-app-2.0'
                }
            }
        }
        
        stage('Deploy') {
            steps {
                echo "deploying the app...."
                sh 'mvn deploy'
            }
        }
    }
}
