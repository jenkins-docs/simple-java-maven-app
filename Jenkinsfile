pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        echo 'first jenkins pipeline'
        sh 'mvn clean compile'
      }
    }

    stage('Unit Test') {
      steps {
        sh 'mvn test'
      }
    }

    stage('Static Analysis') {
      steps {
        sh '''mvn clean verify sonar:sonar \\
  -Dsonar.projectKey=Simple-Java-Maven-New \\
  -Dsonar.host.url=http://3.110.235.71:9000 \\
  -Dsonar.login=sqp_73791b0c81677e7fd093e4901b78740dd8d4174a'''
      }
    }

    stage('Package') {
      steps {
        sh 'mvn package -DskipTests=true'
      }
    }

  }
}