pipeline {
    agent any

    stages {
        stage('Hello') {
            steps {
                echo 'Hello World'
            }
        }
    
    
        stage('clean') {
            steps {
                bat 'mvn clean'
            }
        }
    
       
        stage('pack') {
            steps {
               bat ' mvn clean package'
            }
        }
    
}}
