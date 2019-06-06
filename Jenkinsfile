pipeline {
  agent any
  environment {
    NTA_HOME = 'NTA_HOME_ENVIROMENT_VARIABLE'
  }
  parameters {
    string(name: 'enviroment build', defaultValue: 'dev')
  }
  stages {
    stage('Stage1') {
      steps {
        sh 'printenv'
        sh 'echo NTA Home: ${NTA_HOME}'
      }
    }
    stage('Stage2') {
      steps {
        sh 'echo Stage2'
      }
    }
    stage('Stage3') {
      steps {
        sh 'echo Stage3'
        retry(3) {
          sh 'mvn build'
        }
      }
    }
    stage('Stage4') {
      steps {
        sh 'echo Stage6'
      }
    }
    stage('Stage-Parallel') {
      parallel {
        stage('Stage5') {
          steps {
            sh 'echo Parallel Stage-5'
          }
        }
        stage('Stage6') {
          steps {
            sh 'Parallel Stage-6'
          }
        }
      }
    }
    stage('Back-end') {
      agent {
        docker { image 'maven:3-alpine' }
      }
      steps {
        sh 'mvn --version'
      }
    }
    stage('Front-end') {
      agent {
        docker { image 'node:7-alpine' }
      }
      steps {
        sh 'node --version'
      }
    }
  }
  post {
    always {
      sh 'echo COMPLETED'
    }
    failure {
      mail to: 'tuananhnguyen.ima@gmail.com',
        subject: "Failed Pipeline: ${currentBuild.fullDisplayName}",
        body: "Something is wrong with ${env.BUILD_URL}"
    }
   }
}
