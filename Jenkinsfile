pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        sh 'mvn  clean package'
        sh 'exit'
      }
    }

    stage('exit from image') {
      steps {
        sh 'exit '
      }
    }

    stage('image ') {
      steps {
        sh 'sudo docker build -t javamaven .'
      }
    }

  }
}