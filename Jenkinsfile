pipeline {
    agent {
        label 'docker-agent-alpine'
    }
    
    // tools{
    //     maven 'maven'
    // }
    
    stages {
        stage('Build') { 
            tools{
                maven 'maven'
            }
            steps {
                // sh 'echo PATH: $PATH'
                // sh 'echo start building with mvn, skipping test'
                // sh 'mvn -B -DskipTests clean package'
                sh "echo PATH: $PATH"
                sh "echo start building with mvn, skipping test"
                sh "mvn -B -DskipTests clean package"
                
            }
        }
    }
}
