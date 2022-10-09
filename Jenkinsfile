pipeline {
    agent {
         label "agent1"
    }
    options {
         buildDiscarder logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '3', numToKeepStr: '3')
    }

    stages{
        stage('checkout'){
            steps{
                checkout scm 
            }
        }
        stage('Build'){
            steps{
                sh "mvn --version"
                sh "mvn clean install -DskipTests"
            }
        }
        stage('Test'){
            steps {
                sh "mvn test"
                junit allowEmptyResults: true, testResults: 'target/surfire-reports/*.xml  '
            }
        }
        stage('Deploy'){
            input {
                message 'Do you want me to deploy to UAT?'
            }
            parallel {
                stage('target1'){
                    environment {
                        target_user = "ec2-user"
                        terget_server = "172.31.34.21"
                    }
            
                    steps{
                        echo "Deploying to the Dev Environment"
                        sshagent(['amazon']) {
                            sh "scp -o StrictHostKeyChecking=no target/my-app-1.0-SNAPSHOT.jar $target_user@target_server:/home/ec2-user"
                        }
                    }    
                }

            }
        
        }
    }

}