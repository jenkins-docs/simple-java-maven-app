pipeline {
  agent {
    label 'production'
  }
  stages {
    stage('Build') {
      steps {
        sh 'rm -rf simple-java-maven-app'
        sh 'git clone https://github.com/jenkins-docs/simple-java-maven-app.git'
        sh 'mvn clean -f simple-java-maven-app'
      }
    }

    stage('Test') {
      parallel {
        stage('Test') {
          steps {
            sh 'mvn test -f simple-java-maven-app'
          }
        }

        stage('Test2') {
          steps {
            sh 'echo "Test2"'
          }
        }

      }
    }

    stage('Deploy') {
      steps {
        input 'Do you approve the deployment?'
        echo 'Deploying....'
        sh 'mvn package -f simple-java-maven-app'
      }
    }

  }
  tools {
    maven 'Maven-3.9.9'
  }
}