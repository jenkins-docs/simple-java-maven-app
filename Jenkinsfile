pipeline {
    agent {
        docker {
            image 'maven:3-alpine' 
            args '-v /root/.m2:/root/.m2' 
        }
    }
    stages {
        stage('Build') { 
            steps {
                script {
                    withCredentials([usernameColonPassword(credentialsId: 'fruity', variable: 'USERPASS')]) {
                        def method = load("auth.groovy")
                        method.auth(USERPASS)
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
        stage('SonarQube analysis') {
            environment {
                scanner = tool 'Scanner' 
            }
            steps {
                script {
                    withSonarQubeEnv('Sonarqube') {
                        sh "${scanner}/bin/sonar-scanner"
                    }
                }
            }
        }
        stage('QA') {
            steps {
                timeout(time: 10, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('Deliver') {
            steps {
                sh './jenkins/scripts/deliver.sh'
            }
            post {
                always {
                    withCredentials([usernamePassword(credentialsId: 'art', usernameVariable: 'USR', passwordVariable: 'PASS')]) {
                        rtServer (
                            id: "Artifactory-1",
                            url: "http://172.17.0.3:8081/artifactory",
                            username: "${USR}",
                            password: "${PASS}"
                        )
                        rtUpload (
                            serverId: "Artifactory-1",
                            spec:
                                """{
                                "files": [
                                    {
                                    "pattern": "/home/Documents/simple-java-maven-app/auth.groovy",
                                    "target": "Jenkins-integration/"
                                    }
                                ]
                                }"""
                        )
                    }               
                }
               
            }
        }
    }
}