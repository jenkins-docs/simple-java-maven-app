pipeline {
    def customImage
    agent {
        docker {
            image 'maven:3-alpine' 
            args '-v /root/.m2:/root/.m2' 
        }
    }
    stages {
        stage('Build') { 
            steps {
                sh 'mvn -B -DskipTests clean package' 
                customImage = docker.build("image_of_justice")
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
                customImage.inside {
                sh 'echo "test of justice"'
                }
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