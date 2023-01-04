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
        junit '/var/lib/jenkins/workspace/simple-java-maven-app_pipeline/target/surefire-reports/TEST-com.mycompany.app.AppTest.xml'
      }
    }

  }
}