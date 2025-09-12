pipeline {
    agent none  // assign agents per stage
    tools {
        maven "MAVEN"  // name of Maven installation in Jenkins
    }
    stages {
        stage("Build on Master") {
            agent { label 'built-in' }  // Built-in Windows master
            steps {
                bat "mvn clean package"
                stash includes: 'target/*.jar', name: 'myAppJar'
            }
        }

        stage("Run on Slaves") {
            parallel {
                stage("Run on Server1") {
                    agent { label 'server1' }  // Linux slave1
                    steps {
                        unstash 'myAppJar'
                        sh "java -cp target/my-app-1.0-SNAPSHOT.jar com.mycompany.app.App"
                    }
                }
                stage("Run on Server2") {
                    agent { label 'server2' }  // Linux slave2
                    steps {
                        unstash 'myAppJar'
                        sh "java -cp target/my-app-1.0-SNAPSHOT.jar com.mycompany.app.App"
                    }
                }
            }
        }
    }

    post {
        success {
            archiveArtifacts artifacts: "target/*.jar"
        }
    }
}
