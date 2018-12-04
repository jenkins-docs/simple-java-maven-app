pipeline {
	agent any
	
	stages {	
		stage('Info') {
            steps {
                script {
					String[] targetEnv = params.TARGET_ENV.tokenize (",")
					echo "# envs - ${targetEnv.size()}"
					for (String s: targetEnv) { 
						echo "Parameter $s" 
					}
				}
			}
		}
		stage('GetTools') {
			steps {
				checkout([  
					$class: 'GitSCM', 
					branches: [[name: 'refs/heads/master']], 
					doGenerateSubmoduleConfigurations: false, 
					extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'Tools']], 
					submoduleCfg: [], 
					userRemoteConfigs: [[credentialsId: '1a79b242-5a87-47d0-b801-768d5853b114', url: 'git@github.com:dpriches/build_tools.git']]
				])
			}
		}
		
/*
 		stage('Cleanup') {
			steps {
				step ([$class: 'WsCleanup'])
			}
		}

		stage('qa-deploy') {
			when {
				expression { 
					env.JOB_NAME.endsWith('qa')
				}
			}
			steps {
				script {
					echo "Job is qa"
				}
				
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
