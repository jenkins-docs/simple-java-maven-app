pipeline {
	agent {
		label 'ubuntu'
	}
	
	tools {
		maven 'maven3.8.6'
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
