pipeline {
    agent any
    options {
            skipStagesAfterUnstable()
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Code Analysis') {
                    environment {
                        scannerHome = tool 'Sonar'
                    }
                    steps {
                        script {
                            withSonarQubeEnv('Sonar') {
                                sh "${scannerHome}/bin/sonar-scanner \
                                    -Dsonar.projectKey=<project-key> \
                                    -Dsonar.projectName=<project-name> \
                                    -Dsonar.projectVersion=<project-version> \
                                    -Dsonar.sources=<project-path>"
                            }
                        }
                    }
        }
        stage('Test') {
                    steps {
                        sh 'mvn test'
                    }
                    post {
                        always {
                            junit 'target/surefire-reports/*.xml'
                        }
                    }
        }
        stage('Deliver') {
                    steps {
                        sh './jenkins/scripts/deliver.sh'
                    }
        }
    }
}