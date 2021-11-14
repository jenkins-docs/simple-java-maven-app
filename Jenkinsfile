pipeline {
    agent {label 'fonify'}
    stages {
        stage('feature') {
            when {
                branch 'feature'
            }            
            steps {
                    
                    sh 'ls -lrt'
                    sh 'sleep 10s'
                }
            }
        stage('develop') {
            when {
                branch 'develop'
            }            
            steps {
                    
                    sh 'ls -lrt'
                    sh 'sleep 10s'
                }
            }
         stage('master') {
            when {
                branch 'master'
            }
             steps {
                    
                    sh 'ls -lrt'
                    sh 'sleep 10s'
                }
            }       
        }
 }
