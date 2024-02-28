pipeline {
    agent any

    tools {
        maven "maven3"
    }

    stages {
        stage('Check out') {
            steps {
                git credentialsId: 'webhook', url: 'https://github.com/gurumurthygd/simple-java-maven-app.git'
            }
        }
        
        stage("Build code") {
            steps {
                sh "mvn clean package"
            }
        }
    }
}
