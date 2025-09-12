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
        stage("run"){
            steps{
                bat "java -cp target/my-app-1.0-SNAPSHOT.jar com.mycompany.app.App"
            }
    }

    post {
        always {
            archiveArtifacts artifacts: "**/target/*.jar"
        }
    }
}
