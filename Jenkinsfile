pipeline {
    agent {
        label 'production'
    }
    tools {
        maven 'Maven-3.9.9' // Name of the Maven installation in Jenkins
    }
    stages {
        stage('Build') {
            steps {
                sh "rm -rf simple-java-maven-app"
                sh "git clone https://github.com/jenkins-docs/simple-java-maven-app.git"
                sh "mvn clean -f simple-java-maven-app"

            }
        }
        stage('Test') {
            steps {
                
                sh "mvn test -f simple-java-maven-app"
            }
    }   
        stage('Deploy') {
            steps {
                input 'Do you approve the deployment?'
                echo 'Deploying....'
                sh "mvn package -f simple-java-maven-app"
            }
        }
    }
}
