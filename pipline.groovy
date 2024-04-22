pipeline {
    agent any

    environment {
        // Define the base name for the artifact
        ARTIFACT_NAME = 'myapp'
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
                    // Change the version number based on the branch
                    if (env.BRANCH_NAME == 'dev') {
                        // Feature branch merged into dev
                        env.ARTIFACT_VERSION = '1.0.0-snapshot_dev'
                    } else if (env.BRANCH_NAME == 'release') {
                        // Dev branch merged into release
                        env.ARTIFACT_VERSION = '1.0.0-Final'
                    } else if (env.BRANCH_NAME == 'master') {
                        // Release branch merged into master
                        env.ARTIFACT_VERSION = '1.0.0'
                    } else if (env.BRANCH_NAME == 'feature') {
                        // Default version if branch is not recognized
                        env.ARTIFACT_VERSION = '1.0.0-Feature'
                    }

                    // Update the project version using Maven
                    bat "mvn versions:set -DnewVersion=${env.ARTIFACT_VERSION}"

                    // Ensure the version update is applied
                    bat 'mvn versions:commit'
                }
            }
        }

        stage('Package') {
            steps {
                bat 'mvn clean package'
                script {
                    // Assuming the packaging phase creates a WAR or JAR file, rename and archive the artifact
                    def artifactFilename = "${env.ARTIFACT_NAME}-${env.ARTIFACT_VERSION}.jar" // Change .jar to .war if needed
                    bat "copy target/*.jar target/${artifactFilename}" // Change *.jar to *.war if needed

                    // Archive the artifact in Jenkins
                    archiveArtifacts artifacts: "target/${artifactFilename}", onlyIfSuccessful: true
                }
            }
        }
    }
}
