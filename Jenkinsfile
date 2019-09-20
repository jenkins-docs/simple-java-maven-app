pipeline{
    agent any
    def sonarUrl = 'sonar.host.url=http://172.31.30.136:9000'
    environment{
        PATH = "/opt/maven3/bin:$PATH"
    }
    stages{
        stage("Git Checkout"){
            steps{
                git credentialsId: 'github', url: 'https://github.com/sudharsansadasivam/simple-java-maven-app'
            }
        }
        stage('Sonar Publish'){
               withCredentials([string(credentialsId: 'sonarqube-server', variable: 'sonarToken')]) {
                def sonarToken = "sonar.login=${sonarToken}"
                sh "mvn sonar:sonar -D${sonarUrl}  -D${sonarToken}"
             }

   }
        stage("Maven Build"){
            steps{
                sh "mvn clean package"
                sh "mv target/*.jar target/myweb.jar"
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
                    scp -o StrictHostKeyChecking=no target/myweb.jar  ubuntu@172.31.25.197:/opt/tomcat8/webapps/
                    
                    ssh ubuntu@172.31.25.197 /opt/tomcat8/bin/shutdown.sh
                    
                    ssh ubuntu@172.31.25.197 /opt/tomcat8/bin/startup.sh
                
                """
            }
            
            }
        }
    }
}
