pipeline {
	agent {
		label 'maven'
	}

	stages {
		stage('Build') {
			steps {
				sh 'mvn clean install'
			}
		}
	}
}
