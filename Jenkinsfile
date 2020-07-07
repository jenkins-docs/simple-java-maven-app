pipeline {
  agent any
    
  stages {

    stage('Build') {
      steps {
        sh 'mvn -B -DskipTests clean package'
       }
      post {
        success {
            slackSend channel: 'rally-jenkins-integration', color: '#00ff00', message: "BUILD SUCCESSFUL\nJob Name = ${env.JOB_NAME}\nJob Number = ${env.BUILD_NUMBER}\nJob URL = ${env.BUILD_URL} ",
              teamDomain: 'rallydemo', tokenCredentialId: 'JenkinsSlack'
          }
        failure {
            slackSend channel: 'rally-jenkins-integration', color: '#00ff00', message: "BUILD UNSUCCESSFUL\nJob Name = ${env.JOB_NAME}\nJob Number = ${env.BUILD_NUMBER}\nJob URL = ${env.BUILD_URL} ",
              teamDomain: 'rallydemo', tokenCredentialId: 'JenkinsSlack'
         }
      }
    }
    
    stage('Test') {
      steps {
        sh 'mvn test'
       }
        post {
          always {
            junit 'target/surefire-reports/**/*.xml'
          }
        }
     }
    
    stage('Deploy') {
      
      steps {
        script {
          if (env.BRANCH_NAME == 'master') {
              echo 'master branch deployed'
          } else {
              echo 'PR only'
          }
        }
       }
        
     }
      
  //}
}
