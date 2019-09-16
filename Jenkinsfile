pipeline{
    agent any
    
    environment{
        PATH = "/opt/maven3/bin:$PATH"
    }
    stages{
        stage("Git Checkout"){
            steps{
                git credentialsId: 'github', url: 'https://github.com/sudharsansadasivam/simple-java-maven-app'
            }
        }
        stage("Maven Build"){
            steps{
                sh "mvn clean package"
                //sh "mv target/*.jar target/my-app-1.0-SNAPSHOT.jar"
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage("deploy-dev"){
            steps{
                sshagent(['tomcat-new']) {
                sh """
                    scp -o StrictHostKeyChecking=no target/my-app-1.0-SNAPSHOT.jar  ubuntu@172.31.25.197:/opt/tomcat8/webapps/
                    
                    ssh ubuntu@172.31.25.197 /opt/tomcat8/bin/shutdown.sh
                    
                    ssh ubuntu@172.31.25.197 /opt/tomcat8/bin/startup.sh
                
                """
            }
            
            }
        }
    }
}
