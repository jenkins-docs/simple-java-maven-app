pipeline {
	agent any
	
    stages {
        stage('Build') { 
            steps {
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
            steps {
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
