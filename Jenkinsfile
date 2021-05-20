pipeline {
    agent {
        label 'demo'
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
