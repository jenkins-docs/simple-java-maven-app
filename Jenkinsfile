pipeline {
  agent any
  environment {
    NTA_HOME = 'NTA_HOME_ENVIROMENT_VARIABLE'
  }
  parameters {
    choice(
      choices: ['development', 'staging', 'production'],
      description: 'build environment',
      name: 'REQUESTED_BUILD'
    )
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
    stage('Stage3-Development') {
      when {
        // Only say hello if a "development" is requested
        expression { params.REQUESTED_BUILD == 'development' }
      }
      steps {
        sh 'echo Stage3-development'
      }
    }
    stage('Stage3-Staging') {
      when {
        // Only say hello if a "staging" is requested
        expression { params.REQUESTED_BUILD == 'staging' }
      }
      steps {
        sh 'echo Stage3-staging'
      }
    }
    stage('Stage4') {
      steps {
        sh 'echo Stage4'
        //retry(3) {
        //  sh 'mvn build'
       // }
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
            sh 'echo Parallel Stage-6'
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
