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
                  sh 'docker build  -t 3.106.188.234:8082/my-app:1.0 .'
                  sh "docker login -u  $SERVICE_CREDS_USR -p $SERVICE_CREDS_PSW  3.106.188.234:8082" 
                  sh 'docker push 3.106.188.234:8082/my-app:1.0'
               
            }
        }
    }
}