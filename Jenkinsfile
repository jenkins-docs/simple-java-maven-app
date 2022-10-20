pipeline {
  agent {
    node {
      label 'linux'
    }

  }
  stages {
    stage('Build') {
      environment {
        BUZZ_NAME = 'Worker bee'
      }
      steps {
        sh '''echo Hola soy $BUZZ_NAME
mvn -B -DskipTests clean package'''
        archiveArtifacts(artifacts: 'target/*.jar', fingerprint: true)
      }
    }

    stage('Test') {
      steps {
        sh 'mvn test'
      }
    }

  }
  tools {
    maven 'mvn3'
  }
}