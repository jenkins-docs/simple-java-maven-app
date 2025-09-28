pipeline {
	agent any
	
	tools {
		maven 'M3'
	}
	
	environment {
		// credentials for accessing tomcat servers
		TOMCAT_CREDS = credentials('tomcat-credentials')
	}
	
	stages {
		stage('Checkout') {
			steps {
				git url: 'https://github.com/rajnish107/simple-java-maven-app.git'
				branch: 'master'
			}
		}
		
		stage('Build') {
			steps {
				sh "mvn clean package"
			}
		}
		
		stage('Test') {
			steps {
				sh "mvn test"
			}
		}
		
		stage('Deploy') {
			steps {
				echo 'Deploying to tomcat...'
				sshagent(credentials: [TOMCAT_CREDS] {
					ssh """
						scp -o StrictHostKeyChecking=no \
							target/maven-web-app.war \
							$TOMCAT_CREDS_USR@107.22.82.222:/usr/share/tomcat10/webapps/
						"""
				}
			}
		}
		
	}
	
	post {
		always {
			echo 'pipeline finished'
		}
		
		success {
			echo 'pipeline succeeded'
		}
		
		failure {
			echo 'pipeline failed'
		}
	}
}
