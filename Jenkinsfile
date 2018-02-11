pipeline {
  agent any
  tools {
    maven 'maven-3.5.2'
    jdk 'jdk-1.8.0_162'
  }
  stages {
    stage('Clean') {
        steps {
          sh 'mvn -B -ff -V -U clean'
        }
      }
    stage('Build') {
      steps {
        sh 'mvn -B -fae -V -U install'
      }
      post {
        always {
          junit 'target/surefire-reports/*.xml'
        }
      }
    }
  }
}