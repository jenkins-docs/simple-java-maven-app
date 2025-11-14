pipeline {
  agent any
  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Build') {
      steps {
        sh 'echo Build'
      }
    }

  }
  options {
    skipDefaultCheckout(true)
  }
}