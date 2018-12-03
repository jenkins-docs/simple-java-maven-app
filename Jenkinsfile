pipeline {
	agent any
	
    stages {
        stage('Build') { 
            steps {
                sh 'set -x'
                sh 'echo "Pre build step"'
                sh 'mvn -B -DskipTests clean package' 
                sh 'echo "Post build step"'
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
            input {
                message "Press OK to continue"
                submitter "user1,user2"
		        parameters {
			        string(name:'username', defaultValue: 'user', description: 'Username of the user pressing Ok')
		        }
            }
            steps {
                echo "User: ${username} said OK"
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
