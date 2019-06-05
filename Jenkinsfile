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
                emailext body: 'A Test EMail', recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']], subject: 'Test'
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
