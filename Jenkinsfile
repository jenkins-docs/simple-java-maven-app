pipeline {

    agent any
    
    // agent {
    //     docker {
    //         image 'maven:3.8.1-adoptopenjdk-11'
    //         args '-v /root/.m2:/root/.m2'
    //     }
    // }

    environment {
        AWS_ACCOUNT_ID = "345002264488"
        AWS_DEFAULT_REGION = "us-west-2"
        IMAGE_REPO_NAME = "abaqus/allgeo-hello-world"
        REPOSITORY_URI = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com/${IMAGE_REPO_NAME}"
        CURRENT_VERSION = currentVersion()
        NEXT_VERSION = nextVersion(buildMetadata: "$env.BUILD_NUMBER",writeVersion: true)
        IMAGE_TAG = "${NEXT_VERSION}"
    }

    options {
        skipStagesAfterUnstable()
    }
    stages {

    
        stage('Build') {
            steps {                
                echo "current vesion = ${CURRENT_VERSION}"
                echo "next version = ${NEXT_VERSION}"
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
    }
}
