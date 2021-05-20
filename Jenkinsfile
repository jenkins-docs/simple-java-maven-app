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
               sh "echo $PATH"
             sh "mvn clean install"
             
            }
        }
       
    }
}
