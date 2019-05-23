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
        junit(testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true)
      }
    }
    stage('Informe') {
      steps {
        emailext(subject: 'Je s\'appelle Groot', body: 'Je s\'appelle Groot', from: 'jesappelle@groot.fr', to: 'cyrielle.rech@epsi.fr')
      }
    }
  }
}