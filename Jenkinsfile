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
}
