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
				expression { 
					env.JOB_NAME.endsWith('dev')
				}
			}
			steps {
				echo "Job is dev"
			}
		}
	}
}
