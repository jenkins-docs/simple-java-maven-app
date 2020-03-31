pipeline {
  agent any
  stages {
    stage('develop') {
      steps {
        sh 'mvn -B -DskipTests clean package'
      }
    }

  }
}