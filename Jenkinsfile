pipeline {
	agent any
	
	stages {
		stage('Info') {
            steps {
                echo "Selected params: ${params.TARGET_ENV}"
                script {
						for (int i in params.TARGET_ENV) { echo "Parameter $i" }
				}
			}
		}
/*
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
*/
	}
}
