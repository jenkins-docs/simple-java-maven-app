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
          
          //slackSend channel: 'rally-jenkins-integration', color: '#00ff00', message: "successful Build - Job Name - ${env.JOB_NAME} ${env.BUILD_NUMBER} ${env.BUILD_URL} ",
            //teamDomain: 'rallydemo', tokenCredentialId: 'JenkinsSlack'
          
          slackSend channel: 'rally-jenkins-integration', color: '#00ff00', message: "Successful Build\nJob Name = ${env.JOB_NAME}\nJob Number = ${env.BUILD_NUMBER}\nJob URL = ${env.BUILD_URL} ",
            teamDomain: 'rallydemo', tokenCredentialId: 'JenkinsSlack'
        }
      }
    }
  }
}
