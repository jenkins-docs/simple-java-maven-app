pipeline {
  agent any
  stages {
    stage('RallyBuild') {
      steps {
        sh './jenkins/build.sh'
      }
    }

    stage('RallyTest') {
      steps {
        sh './jenkins/test-all.sh'
      }
    }

  }
}