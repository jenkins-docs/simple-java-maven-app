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
        sh 'mvn clean package'
      }
    }

    stage('image ') {
      steps {
        sh 'sudo docker build -t javamaven .'
      }
    }

  }
}