pipeline {
  agent {
    docker {
      image 'maven:3-alpine'
      args '-v /tmp:/tmp'
    }

  }
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