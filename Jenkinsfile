pipeline {
    agent none  // we will assign agents per stage
    tools {
        maven "MAVEN"  // Must match your Maven installation name in Jenkins
    }
    stages {
        stage("Build") {
            agent { label 'Master' }  // build on master
            steps {
                bat "mvn clean package"
            }
        }
        stage("Run on Slaves") {
            parallel(
                slave1Job: {
                    agent { label 'slave1' }
                    steps {
                        echo "Running on slave1"
                        bat "java -cp target\\my-app-1.0-SNAPSHOT.jar com.mycompany.app.App"
                    }
                },
                slave2Job: {
                    agent { label 'slave2' }
                    steps {
                        echo "Running on slave2"
                        bat "java -cp target\\my-app-1.0-SNAPSHOT.jar com.mycompany.app.App"
                    }
                }
            )
        }
    }
    post {
        success {
            archiveArtifacts artifacts: "**\\target\\*.jar"
        }
    }
}
