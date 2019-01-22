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
        stage('Deliver') {
            steps {
                sh './jenkins/scripts/deliver.sh'
                rtServer (
                    id: "Artifactory-1",
                    url: "http://172.17.0.3:8081/artifactory",
                    username: "zac",
                    password: "abcd123"
                )
                rtUpload (
                    serverId: "Artifactory-1",
                    spec:
                        """{
                        "files": [
                            {
                            "pattern": "simple-java-maven-app/*.groovy",
                            "target": "Jenkins-integration/"
                            }
                        ]
                        }"""
                )
            }
        }
    }
}