pipeline {
  agent {
    docker {
      image 'maven:3.8.1-openjdk-11'
    }

  }
  stages {
    stage('Build') {
      steps {
        sh 'mvn clean compile'
      }
    }

  }
}