pipeline {
  agent {
    docker {
      args '-v $HOME/.m2:/root/.m2'
      image 'maven:3.8.1-openjdk-11'
    }

  }
  stages {
    stage('Build') {
      steps {
        sh 'mvn clean compile'
        sh 'rm -rf ~/.m2/repository'
      }
    }

    stage('Test') {
      steps {
        sh 'mvn test'
      }
    }

    stage('Package') {
      steps {
        sh 'mvn package -DskipTests -j 4'
      }
    }

  }
}