pipeline {
  agent any
  
  
  stages {

    stage('Build') {
      steps {
        sh 'mvn -Dmaven.test.failure.ignore=true install'
      }
      post {
        success {
          junit 'target/surefire-reports/**/*.xml'
          
          // slackSend (channel: 'rally-jenkins-integration', color: '#00ff00', message: 'successful', tokenCredentialId: 'JenkinsSlack')
          
          //slackSend channel: 'rally-jenkins-integration', color: '#00ff00', message: "successful ${env.JOB_NAME} ${env.BUILD_NUMBER} ${env.BUILD_URL} ",
            //teamDomain: 'rallydemo', tokenCredentialId: 'JenkinsSlack'
          
          slackSend channel: 'rally-jenkins-integration', color: '#00ff00', message: 'SUCCESSFUL' "JOB NAME = ${env.JOB_NAME} BUILD # =  ${env.BUILD_NUMBER} BUILD URL = ${env.BUILD_URL}", teamDomain: 'rallydemo', tokenCredentialId: 'JenkinsSlack'
        }
      }
    }
  }
}
