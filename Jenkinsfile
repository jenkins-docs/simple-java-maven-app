pipeline {
    agent none  // assign agents per stage
    tools {
        maven "MAVEN"  // must match Maven tool name in Jenkins
    }
    stages {
        stage("Build") {
            agent { label 'built-in' }  // build on Windows master
            steps {
                bat "mvn clean package"
            }
        }
        stage("Run on Slaves") {
            parallel {
                stage("Run on Slave1") {
                    agent { label 'slave1' }  // Linux slave
                    steps {
                        echo "Running on slave1"
                        sh "java -cp target/my-app-1.0-SNAPSHOT.jar com.mycompany.app.App"
                    }
                }
                stage("Run on Slave2") {
                    agent { label 'slave2' }  // Linux slave
                    steps {
                        echo "Running on slave2"
                        sh "java -cp target/my-app-1.0-SNAPSHOT.jar com.mycompany.app.App"
                    }
                }
            }
        }
    }
    post {
        success {
            archiveArtifacts artifacts: "**/target/*.jar"
        }
    }
}
