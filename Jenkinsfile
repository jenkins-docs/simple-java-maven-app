pipeline {
    agent any

    tools {
          jdk '11.0.15_8'
          maven 'Maven-3.8.5'
    }

    stages {
        stage('Build') {
            steps {
                git credentialsId: 'github-pat', url: 'https://github.com/molnare/simple-java-maven-app.git'
                sh "mvn -Dmaven.test.failure.ignore=true clean package"
            }

            post {
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
    }
}
