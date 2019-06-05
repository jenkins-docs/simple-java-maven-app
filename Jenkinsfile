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
          mail to: 'tuananhnguyen.ima@gmail.com',
             subject: "Failed Pipeline: ${currentBuild.fullDisplayName}",
             body: "Something is wrong with ${env.BUILD_URL}"
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
