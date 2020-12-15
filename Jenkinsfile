pipeline {
  agent {
    docker {
      image 'maven:3-alpine'
      args '-p 3000:3000'
    }

  }
  stages {
    stage('build') {
      steps {
        sh 'mvn   package'
        sh 'exit'
      }
    }

    stage('exit from image') {
      steps {
        sh 'exit '
      }
    }

    stage('image ') {
      agent {
        docker{
      steps {
        sh 'sudo docker build -t javamaven .'
      }
    }
      }
    }
  }
}
