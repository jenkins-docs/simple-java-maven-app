pipeline {
    
    agent none
    
    parameters {
        string(name: 'X' , defaultValue: 'myslavemaven', description: 'enter ur name ? ')
    }
    
    stages {
        
        stage('Build'){
            
            agent {
                label "${params.X}"
            }
            
            steps {
                echo "my name is  :  ${params.X}"
                echo "my first step in build stage"
                git 'https://github.com/vimallinuxworld13/simple-java-maven-app.git'
                sh "ls -l"
                sh "pwd"
                stash includes: '*' , name: 'myapp'
                
            }
        }
        
        stage('Test'){
            
             agent {
                label 'myslavemaven'
            }
            
            steps {
                echo "test"
                unstash 'myapp'
                sh 'mvn package'
                stash includes: 'target/*.jar', name: 'myjar'
            }
        }
        
        
        
         stage('Deploy'){
             when {
              branch "fix-*"   
             }
              agent {
                label 'myslavemaven'
            }
            
            steps {
                echo "deploy"
                unstash 'myjar'
                sh 'java -jar *.jar'
            }
        }
    }
}
