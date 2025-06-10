pipeline {
  agent any

  tools {
    maven 'Maven-3.8.1'
    jdk 'jdk-21'
  }

  stages {
    stage('Build App Repo') {
      steps {
        echo 'Building primary app...'
         bat 'mvn clean install'
      }
    }

    stage('Checkout Source') {
      steps {
        checkout([$class: 'GitSCM',
          branches: [[name: '*/master']],
          userRemoteConfigs: [[
            url: 'https://github.com/Syedrayyangithub/simple-java-maven-project.git',
            credentialsId: 'github-creds'
          ]]
        ])
      }
    }

    stage('Build Secondary Repo') {
      steps {
        echo 'Building second repo...'
        bat 'mvn clean install'
      }
    }
  }
}
