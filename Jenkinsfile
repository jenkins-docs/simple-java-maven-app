pipeline {
  agent {
    docker {
      args '-v $HOME/.m2:/root/.m2'
      image 'maven:3.3.1-jdk-8'
    }

  }
  stages {
    stage('Build') {
      steps {
        sh 'mvn clean compile'
      }
    }

    stage('Test') {
      steps {
        sh 'mvn test'
      }
    }

    stage('Package') {
      steps {
        sh 'mvn package -DskipTests'
      }
    }

  }
}