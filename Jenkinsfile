pipeline {
  agent any
  stages {
    stage('init') {
      steps {
        sh 'docker version'
      }
    }

  }
  options {
    skipDefaultCheckout(true)
  }
}