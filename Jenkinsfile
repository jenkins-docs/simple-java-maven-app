pipeline {
	agent {
		label 'ubuntu'
	}
	
	tools {
		maven 'maven3.8.6'
	}

	stages {
		stage('Build') {
			steps {
				sh 'mvn clean install'
			}
		}
		
		stage('Test') {
			sh 'mvn test'
		}
	}
}
