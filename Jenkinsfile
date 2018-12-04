pipeline {
	agent {
		label 'master'
	}

	stages {
		stage('Build') {
			steps {
				sh 'echo "Hello"'
			}
		}
		stage('Deploy') {
			when {
				env.JOB_NAME.endsWith('dev')
			}
			steps {
				sh 'echo "Job is dev"'
			}
		}
	}
}
