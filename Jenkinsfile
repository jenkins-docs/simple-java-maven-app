pipeline {
  agent {
    docker {
      image 'maven:3-alpine'
      label 'my-defined-label'
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