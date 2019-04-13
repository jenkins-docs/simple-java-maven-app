pipeline {
    agent {
        docker {
            image 'maven:3-alpine' 
        }
    }
    stages {
        stage('Build') { 
            steps {
                sh 'mvn -Dmaven.repo.local=.m2 -X -DskipTests clean package' 
            }
        }
    }
}
