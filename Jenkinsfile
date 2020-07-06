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
          
          slackSend (channel: 'rally-jenkins-integration', color: '#00ff00', message: 'successful', tokenCredentialId: 'JenkinsSlack')
        }
      }
    }
  }
}
