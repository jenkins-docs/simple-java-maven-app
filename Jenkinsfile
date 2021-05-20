pipeline {
    agent {
        label 'agent12'
    }
    
    stages{
        stage('checkout'){
            steps{
            checkout scm
            
            }
        }
        stage('Build'){
            steps{
             sh "sh mvn clean install"
            }
        }
       
    }
}
