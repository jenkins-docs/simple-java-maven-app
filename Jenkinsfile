pipeline {
    agent any
    
    triggers {
        pollSCM ('*/5 * * * *')
    }

/* a comment */ 

    stages {
		stage('Info') {
			when (env.JOB_NAME.endsWith('dev'))
				echo "Running ${env.JOB_NAME}"
        }
    }
}
