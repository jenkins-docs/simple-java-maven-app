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

    stage('Test') {
      steps {
        sh '''mvn test
junit \'**/surefire-reports/**/*.xml\''''
        junit '**/surefire-reports/**/*.xml'
      }
    }

  }
  tools {
    maven 'mvn3'
  }
}