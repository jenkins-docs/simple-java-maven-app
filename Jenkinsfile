pipeline {
  agent any
  stages {
    stage('Build') {
      agent any
      steps {
        sh 'mvn -B -DskipTests clean package'
      }
    }

  }
}