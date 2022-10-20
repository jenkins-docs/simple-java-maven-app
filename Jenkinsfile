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
        archiveArtifacts(artifacts: 'target/*.jar', fingerprint: true)
      }
    }

  }
  tools {
    maven 'mvn3'
  }
}