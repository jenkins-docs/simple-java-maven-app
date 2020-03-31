pipeline {
  agent any
  stages {
    stage('test') {
      steps {
        sh 'mvn test'
      }
    }

    stage('build') {
      agent {
        docker {
          image 'maven:3-alpine'
          args '-v /root/.m2:/root/.m2'
        }

      }
      steps {
        sh 'mvn compile'
      }
    }

    stage('develop') {
      steps {
        sh 'mvn -B -DskipTests clean package'
      }
    }

  }
}