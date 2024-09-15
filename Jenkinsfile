pipeline {
    agent {
        label 'docker-agent-alpine'
    }
    
    stages {
        stage('Build') { 
            steps {
                sh 'echo start building with mvn, skipping test'
                sh 'mvn -B -DskipTests clean package' 
                
            }
        }
    }
}
