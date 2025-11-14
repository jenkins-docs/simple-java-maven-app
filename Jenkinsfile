pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        checkout scm
        sh 'echo \'123\''
      }
    }

  }
  options {
    skipDefaultCheckout(true)
  }
}