pipeline {
  agent {
    node {
      label 'linux'
    }

  }
  stages {
    stage('Build') {      
      steps {        
mvn -B -DskipTests clean package'''
        archiveArtifacts(artifacts: 'target/*.jar', fingerprint: true)
      }
    }

    stage('Test') {
      parallel {
        stage('Test') {
          steps {
            sh 'mvn test'
          }
        }

        stage('Testing B') {
          steps {
            sh '''sleep 10
echo done.'''
          }
        }

      }
    }

  }
  tools {
    maven 'mvn3'
  }
}
