pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
git credentialsId: '3dd40017-55b4-4298-a1d2-1eb007b01b6e', url: 'https://github.com/rajaduraivka/simple-java-maven-app.git'                echo 'Building..'
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
