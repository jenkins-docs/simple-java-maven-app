pipeline {
    agent any

    tools {
        maven "MAVEN"  // Make sure "MAVEN" matches your Jenkins Maven tool name
    }

    stages {
        stage('Build') {
            steps {
                bat "mvn clean package"  // 'build' is not a default Maven goal; use 'package'
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: "**/target/*.war"
        }
    }
}
