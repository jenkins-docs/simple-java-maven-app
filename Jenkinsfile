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
                sh "mv target/*.war target/myweb.war"
            }
        }
        stage("deploy-dev"){
            steps{
                sshagent(['tomcat-new']) {
                sh """
                    scp -o StrictHostKeyChecking=no target/myweb.war  ubuntu@18.188.140.158:/opt/tomcat8/webapps/
                    
                    ssh ubuntu@18.188.140.158 /opt/tomcat8/bin/shutdown.sh
                    
                    ssh ubuntu@18.188.140.158 /opt/tomcat8/bin/startup.sh
                
                """
            }
            
            }
        }
    }
}
