pipeline {
	agent any
	
 	environment {
		someval = ''
	}

	stages {	

// Do some prep work for the build
		stage('Prep') {
			steps {
				echo "branch is ${env.BRANCH}"
			}
		}

// Build the app without running tests. Separates compilation errors from test errors		
		stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
	}
	post {
		always {
			deleteDir ()
		}
	}
}
