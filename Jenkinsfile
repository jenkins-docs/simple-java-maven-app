pipeline {
    agent any
    tools{
        maven "MAVEN"
    stages {
        stage('Build') {
            steps {
                bat "mvn clean build"
            }
        post {
              always {
                archiveArtifacts artifacts="**/target/*.war"
              }
            }

        }

    }
}
