pipeline {
	agent {
		label 'ubuntu'
	}
	
	tools {
		maven 'maven3.8.6'
	}

	environment {
		TARGET_USER = "centos"
		TARGET_SERVER = "172.31.6.122"
	}

	options {
		timestamps()
		timeout(10)
		buildDiscarder logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '5', numToKeepStr: '5')
	}

	stages {
		stage('Build') {
			steps {
				sh 'mvn clean install -DskipTests'
			}
		}
		
		stage('Test') {
			steps {
				echo 'Test stage...'
				sh 'mvn test'
			}
		}

		stage('Deploy') {
			steps {
				echo "Deploying to DEV environment..."
				sshagent(['ssh-agent-key']) {
				    sh 'scp -o StrictHostKeyChecking=no target/*.jar ${TARGET_USER}@${TARGET_SERVER}:/home/centos'
				}
			}
		}
	}

	post {
		always {
			// delete workspace dir in agent
			deleteDir()
		}

		success {
			echo "Job successful..."
		}

		failure {
			echo "sendmail -s Maven Job Failed contact@company.com"
		}

	}
}
