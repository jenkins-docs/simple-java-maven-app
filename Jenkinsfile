pipeline {
    agent {
        docker {
            image 'maven:3.8.1-adoptopenjdk-11'
            args '-v /root/.m2:/root/.m2'
        }
    }

    environment {
        AWS_ACCOUNT_ID = "345002264488"
        AWS_DEFAULT_REGION = "us-west-2"
        IMAGE_REPO_NAME = "abaqus/allgeo-hello-world"
        IMAGE_TAG = "latest"
        REPOSITORY_URI = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com/${IMAGE_REPO_NAME}"
        CURRENT_VERSION = currentVersion()
        NEXT_VERSION = nextVersion(writeVersion: true, buildMetadata: "$env.BUILD_NUMBER")
    }

    options {
        skipStagesAfterUnstable()
    }
    stages {

        stage('Hello') {
            steps {
                echo "current vesion = ${CURRENT_VERSION}"
                echo "next version = ${NEXT_VERSION}"
            }
        }

        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
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

        stage('Logging into AWS ECR') {
            steps {
                script {
                    sh "aws ecr get-login-password — region ${AWS_DEFAULT_REGION} | docker login — username AWS — password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com"
                }
            }
        }        
    }
}
