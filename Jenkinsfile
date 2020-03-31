pipeline {
  agent any
  stages {
    stage('test') {
      steps {
        sh 'mvn test'
      }
    }

    stage('develop') {
      steps {
        sh 'mvn -B -DskipTests clean package'
      }
    }

  }
}