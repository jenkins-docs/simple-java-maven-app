pipeline {
  agent any
  stages {
    stage('Stage1') {
      steps {
        sh 'echo Hello'
      }
    }
    stage('Stage2') {
      steps {
        sh 'echo Stage2'
      }
    }
    stage('Stage3') {
      steps {
        sh 'echo Stage3'
        go build
      }
      post {
          failure {
              mail bcc: '', body: "<b>Example</b><br>Project: ${env.JOB_NAME} <br>Build Number: ${env.BUILD_NUMBER} <br> URL de build: ${env.BUILD_URL}", cc: '', charset: 'UTF-8', from: '', mimeType: 'text/html', replyTo: '', subject: "ERROR CI: Project name -> ${env.JOB_NAME}", to: "tuananhnguyen.ima@gmail.com";
          }
      }
    }
    stage('Stage4') {
      steps {
        sh 'echo Stage4'
      }
    }
    stage('Stage5') {
      steps {
        sh 'echo Stage5'
      }
    }
    stage('Stage6') {
      steps {
        sh 'echo Stage6'
      }
    }
  }
}
