pipeline {
    agent any
    
    triggers {
        pollSCM ('*/5 * * * *')
    }

/* a comment */ 

    stages {
		stage('Info') {
            steps {
				echo "Running ${env.JOB_NAME}"
			}
        }
    }
}
