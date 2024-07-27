pipeline {
    agent any
      tools {
        maven 'maven-3.9' 
    } 

    triggers {
        pollSCM('* * * * *') // This is a fallback polling trigger, if webhooks fail
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
    sh 'docker build  -t 13.239.57.69:8082/my-app:1.0 .'
    sh "docker login -u  $SERVICE_CREDS_USR -p $SERVICE_CREDS_PSW  13.239.57.69:8082"
    sh 'docker push 13.239.57.69:8082/my-app:1.0'
               
            }
        }
    }
}