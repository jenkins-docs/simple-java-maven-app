pipeline {
  agent {
    node {
      label 'linux'
    }

  }
  stages {
    stage('Build') {
      steps {
        sh 'mvn -B -DskipTests clean package'
      }
    }

  }
  tools {
    maven 'mvn3'
  }
}