pipeline {
    agnent {
        label 'agnet12'
    }
    stages{
        stage('checkout'){
            steps{
                checkout scm
            }
            
        }
        stage('build'){
            steps{
                sh "mvn clean install"
            }
        }
    }
    
}
