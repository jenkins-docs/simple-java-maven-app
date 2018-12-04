pipeline {
    agent any
    
    triggers {
        pollSCM ('*/5 * * * *')
    }

/* a comment */ 

    stages {
		stage('Info') {
			when {
				expression { ${env.JOB_NAME}.endsWith ("dev") }
			}
            steps {
				echo "Running ${env.JOB_NAME}"
			}
        }
    }
}
