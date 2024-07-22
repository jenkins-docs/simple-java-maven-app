pipeline {
    agent any
      tools {
        maven 'maven-3.9' 
    }
    stages {
        stage('Building Jar') {
            steps {
                echo 'Building Jar'
                sh 'mvn package'

               
            }
        }

         stage('Building image') {
            environment {
                SERVICE_CREDS = credentials('nexus')
            }
            steps {
                echo 'Building Docker Image'
                  sh 'echo "Service user is $SERVICE_CREDS_USR"'
                sh 'echo "Service password is $SERVICE_CREDS_PSW"'
               
            }
        }
    }
}