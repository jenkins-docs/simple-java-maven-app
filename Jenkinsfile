pipeline {
    agent any

    tools {
        maven "MAVEN"
    }

    stages {
        stage('Build') {
            steps {
                bat "mvn clean package"
            }
        }

        stage('Run') {
            steps {
                bat "java -cp target/my-app-1.0-SNAPSHOT.jar com.mycompany.app.App"
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: "**/target/*.jar"
        }
    }
}
