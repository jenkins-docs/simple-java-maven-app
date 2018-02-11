pipeline {
  agent any
  tools {
    maven 'maven-3.5.2'
    jdk 'jdk-1.8.0_162'
  }
  stages {
    stage('Build') {
      steps {
        sh 'mvn --batch-mode -V -U -e clean package'
      }
    }
    stage('Test') {
      steps {
        sh 'mvn --batch-mode -V -U -e'
      }
      post {
        always {
          junit 'target/surefire-reports/*.xml'
        }
      }
    }
  }
}