pipeline {
    agent any
	def mvnHome
	
    stages {
     	stage('Preparation') { // for display purposes
			mvnHome = tool 'Maven3.5.2'
		}	 
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