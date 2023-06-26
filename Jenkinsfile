pipeline {
    agent any

    tools {
          jdk 'OpenJDK-17.0.7-LTS-AArch46'
          maven 'Maven-3.9.2'
    }

    stages {
        stage('Build') {
            steps {
                sh "mvn -Dmaven.test.failure.ignore=true clean package"
            }

            post {
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }

        stage('SonarQube') {
            steps {
                withSonarQubeEnv('CalypsoBinarSonarQubeScanner') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
        
    }
}
