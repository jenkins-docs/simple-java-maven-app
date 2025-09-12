pipeline {
    agent any
    tools {
        maven "MAVEN" // Ensure this matches your Maven installation name in Jenkins
    }
    stages {
        stage("build") {
            steps {
                bat "mvn clean package"
            }
        }
        stage("test") {
            steps {
                parallel(
                    test1: {
                        echo "test 1"
                    },
                    test2: {
                        echo "test 2"
                    }
                )
            }
        }
        stage("run") {
            steps {
                bat "java -cp target\\my-app-1.0-SNAPSHOT.jar com.mycompany.app.App"
            }
        }
    }
    post {
        success {
            archiveArtifacts artifacts: "**\\target\\*.jar"
        }
    }
}
