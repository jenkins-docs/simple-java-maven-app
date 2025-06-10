pipeline {
  agent any

  tools {
    maven 'Maven-3.8.1' // Match this with Jenkins global tool config
    jdk 'jdk-21'        // Or jdk-17, depending on what you've configured
  }

  stages {
    stage('Checkout') {
      steps {
        git url: 'https://github.com/Syedrayyangithub/simple-java-maven-project.git', branch: 'main'
      }
    }

    stage('Build') {
      steps {
        sh 'mvn clean compile'
      }
    }

    stage('Test') {
      steps {
        sh 'mvn test'
        junit '**/target/surefire-reports/*.xml'
      }
    }

    stage('Package') {
      steps {
        sh 'mvn package'
        archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
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


  }
}
