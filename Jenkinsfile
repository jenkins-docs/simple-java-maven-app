pipeline {
    agent any
	environment { // global settings
			mvnHome = tool 'Maven3.5.2'
		}	
	
    stages {
     	 
		stage('Build') {
            steps {
           		bat(/"${mvnHome}\bin\mvn" -B -DskipTests clean package/)
            }
        }
        stage('Test') {
            steps {
                bat(/"${mvnHome}\bin\mvn" test/)
            }
            post {
                always {
                   junit '**/target/surefire-reports/*.xml'
                }
            }
        }
    }
}