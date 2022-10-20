pipeline {
  agent {
    node {
      label 'linux'
    }

  }
  stages {
    stage('Build') {
      steps {
        sh 'mvn3 -B -DskipTests clean package'
      }
    }

  }
}