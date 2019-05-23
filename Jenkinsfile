pipeline {
  agent {
    docker {
      image 'maven:3-alpine'
      args '-v /root/.m2:/root/.m2'
    }

  }
  stages {
    stage('Build') {
      steps {
        sh 'mvn -B -DskipTests clean package'
      }
    }
    stage('Test') {
      steps {
        sh 'mvn test'
        junit(testResults: 'arget/surefire-reports/*.xml', allowEmptyResults: true, healthScaleFactor: 2)
      }
    }
    stage('Inform'){
      steps {
        emailext body: 'essai', subject: 'test', to: 'jojo@titi'
      }
    }
  }
}