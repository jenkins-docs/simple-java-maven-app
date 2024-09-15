pipeline {
    agent {
        label 'docker-agent-alpine'
    }

    tools{
         maven 'maven_3.9.9'
    }    
    
    stages {
        stage('Build') { 
            steps {
                sh "echo start building with mvn, skipping test"
                sh "mvn -B -DskipTests -Denforcer.skip=true clean package"
                
            }
        }

         stage('Unit Test') {
            steps {
                script {
                    sh 'echo Running test'
                    sh "mvn test"
                    sh "ls -lR target"
                }
            }
        }
    }

    

    post {
        success {
            archiveArtifacts artifacts: '**/*.jar,**/*.war,target/surefire-reports/*.xml',
                   allowEmptyArchive: true,
                   fingerprint: true,
                   onlyIfSuccessful: true
        }

        cleanup {
            cleanWs(cleanWhenNotBuilt: false,
                    deleteDirs: true,
                    disableDeferredWipeout: true,
                    notFailBuild: true)
        }
    }
}
