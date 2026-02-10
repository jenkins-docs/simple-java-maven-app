pipeline {
    agent any

    tools {
        jdk 'Java-21'
        maven 'Maven-3.9.9'
    }

    stages {
        stage('Checkout') {
            steps {
                // This is where your snippet goes
                git branch: 'BranchA',
                    url: 'https://github.com/kamlab/simple-java-maven-app.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn -version'   // optional: verify correct Maven
                sh 'java -version'  // optional: verify correct Java
                sh 'mvn clean package'
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
        }
    }
}
