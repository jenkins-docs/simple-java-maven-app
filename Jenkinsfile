pipeline {
	agent any
	
 	environment {
		someval = ''
	}

	stages {	

// Do some prep work for the build - add branch publishing - feature/b2
		stage('Prep') {
			steps {
				echo 'Branch is ' + env.BRANCH_NAME // from f/b2
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
