pipeline {
  agent any
  stages {
    stage('Build'){
      steps {
        sh '/opt/apache-maven-3.9.6/bin/mvn -B -DskipTests clean package' 
      }
    }
    stage('Test'){
      steps {
        sh '/opt/apache-maven-3.9.6/bin/mvn test'
      }
      post {
        always{
          junit 'target/surefire-reports/*.xml'
        }
      }
    } 
  }
}
