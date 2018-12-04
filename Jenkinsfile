pipeline {
    agent any
    
    triggers {
        pollSCM ('*/5 * * * *')
    }

/* a comment */ 

    stages {
		stage('Info') {
            when {
				${env.JOB_NAME}.endsWith ("dev")
			}
            steps {
				echo "Running dev build"
			}
        }
    }
}
