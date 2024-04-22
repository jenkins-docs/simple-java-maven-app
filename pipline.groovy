pipeline {
    agent any

    environment {
        ARTIFACT_NAME = 'myapp' // Define the base name for the artifact
    }

    stages {
        stage('Hello') {
            steps {
                echo 'Hello World'
            }
        }
    
        stage('Clean') {
            steps {
                bat 'mvn clean'
            }
        }
    
        stage('Build and Artifact') {
            steps {
                script {
                    // Set the artifact version based on the branch
                    if (env.BRANCH_NAME == 'dev') {
                        env.ARTIFACT_VERSION = '1.0.0-snapshot_dev'
                    } else if (env.BRANCH_NAME == 'release') {
                        env.ARTIFACT_VERSION = '1.0.0-RC'
                    } else if (env.BRANCH_NAME == 'master') {
                        env.ARTIFACT_VERSION = '1.0.0'
                    } else {
                        env.ARTIFACT_VERSION = '1.0.0-unknown'
                    }

                    // Update the project version using Maven
                    bat "mvn versions:set -DnewVersion=${env.ARTIFACT_VERSION}"
                    bat 'mvn versions:commit'
                }
            }
        }

        stage('Package') {
            steps {
                bat 'mvn clean package'
                
            }
        }
    }
}
