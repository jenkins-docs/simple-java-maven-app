pipeline {
	agent any

	parameters {
		choice (
			choices: 'env-a\n\env-b',
			description: 'choose env'
			name: 'target'
	}
	
	stages {
		stage('Info') {
            steps {
                echo "Running ${env.BUILD_ID} on ${env.JENKINS_URL}"
            }
        }

		stage('qa-deploy') {
			when {
				expression { 
					env.JOB_NAME.endsWith('qa')
				}
			}
			steps {
				echo "Job is qa"
			}
		}

		stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'app/target/surefire-reports/*.xml'
                }
            }
        }

        stage('UploadArtifact') {
            steps {
                sh 'mvn deploy'
            }
        }

        stage('GenerateRpms') {
            steps {
                sh 'mvn deploy -P create-rpms -f create-rpms/pom.xml'
            }
        }

		stage('dev-deploy') {
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
