pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
            git credentialsId: '6fc1abe4-f4c7-4338-9085-1c7075dc3a4e', url: 'https://github.com/rajaduraivka/funny.git'
                echo 'Building..'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn sonar:sonar \
  -Dsonar.projectKey=devaraj \
  -Dsonar.organization=devraj \
  -Dsonar.host.url=https://sonarcloud.io \
  -Dsonar.login=8c51cd12ba5c6fe4d2b0634e14c12f6b8690c6a0'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
        stage('Compile'){
            steps{
                echo 'Compile..'
            }
        }
    }
}
