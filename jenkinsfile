pipeline {
  agent any
  stages {
    stage('Build'){
      steps{
        sh'/opt/apache-maven-3.9.6/bin/mvn -B -DskipTest clean package'
      }
    }
  }
}
