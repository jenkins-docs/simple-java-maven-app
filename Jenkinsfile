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
/*            input {
                message "Press OK to continue"
                parameters {
                    string(name:'PERSON', defaultValue: 'user', description: 'Username of the user pressing Ok')
                }
            }
            */
            steps {
                /*echo "User: ${PERSON} said OK"*/
                sh 'mvn deploy'
            }
        }

        stage('GenerateRpms') {
            steps {
                sh 'mvn deploy -P create-rpms -f create-rpms/pom.xml'
            }
        }
    }
}
