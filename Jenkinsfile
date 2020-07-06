pipeline {
  agent any
  
  
  stages {

    stage('Build') {
      steps {
        //sh 'mvn -Dmaven.test.failure.ignore=true install'
        
        sh 'mvn -B -DskipTests clean package'
      }
      post {
        success {
          junit 'target/surefire-reports/**/*.xml'
          
          slackSend channel: 'rally-jenkins-integration', color: '#00ff00', message: "BUILD SUCCESSFUL\nJob Name = ${env.JOB_NAME}\nJob Number = ${env.BUILD_NUMBER}\nJob URL = ${env.BUILD_URL} ",
            teamDomain: 'rallydemo', tokenCredentialId: 'JenkinsSlack'
        }
        failure {
          slackSend channel: 'rally-jenkins-integration', color: '#00ff00', message: "BUILD UNSUCCESSFUL\nJob Name = ${env.JOB_NAME}\nJob Number = ${env.BUILD_NUMBER}\nJob URL = ${env.BUILD_URL} ",
            teamDomain: 'rallydemo', tokenCredentialId: 'JenkinsSlack'
        }
      }
    }
  }
}
