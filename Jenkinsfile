pipeline {
    agent {
        label 'docker-agent-alpine'
    }
    
    // tools{
    //     maven 'maven_3.8.5'
    // } 
    
    stages {
        stage('Build') { 
            tools{
                // maven 'maven_3.8.5'
                 maven 'maven_3.9.9'
            }
            steps {
                // sh 'echo PATH: $PATH'
                // sh 'echo start building with mvn, skipping test'
                // sh 'mvn -B -DskipTests clean package'
                sh "echo PATH: $PATH"
                sh "mvn --version"
                sh "echo start building with mvn, skipping test"
                sh "mvn -B -DskipTests -Denforcer.skip=true clean package"
                
            }
        }
    }

    post {
        success {
            archiveArtifacts artifacts: 'target/my-app-1.0-SNAPSHOT.jar',
                   allowEmptyArchive: true,
                   fingerprint: true,
                   onlyIfSuccessful: true
        }
    }
}
