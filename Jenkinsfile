pipeline {

agent any

tools {
        maven 'maven 1.0'
        sonarqube 'SonarQube Scanner'
    }
stages{
    stage("Checkout"){
        steps{
            echo "Downloading Source Code"
            git 'https://github.com/prashhantss/simple-java-maven-app.git'
            sh "ls -l"
            sh "pwd"
        }
    }

    stage('Build') {
        steps {
            withMaven(maven: 'maven 1.0') {
            sh 'mvn clean package'
                }
            }
        }

        
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('Sonar Scanner') {
                    sh 'mvn sonar:sonar'
                }
            }
        }      
        
        
        
        
}
        
        
        
}
