pipeline {

agent any

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

        
    stage('SonarQube analysis') {
        steps{
            withSonarQubeEnv('sonarqube-8.9.10') { 
            sh "mvn sonar:sonar"
            }
        }
    }    
        
}
        
        
        
}
