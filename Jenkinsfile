pipeline{
    agent any
    stages{
        stage('Build'){
            steps{
                bat 'mvn clean package'
            }
        }
        stage('Docker Build'){
            steps{
                script{
                    dockerImage = docker.build("simple-java-maven-app")
                }
            }
        }
        stage('Docker Run'){
            steps{
                script{
                    dockerImage.run('-p 8080:8080')
                }
            }
        }
    }
}