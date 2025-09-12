pipeline {
    agent none
    stages {
        stage('Build on Master') {
            agent { label 'built-in' }
            tools { git 'Default'; maven 'MAVEN' }
            steps {
                bat "mvn clean package"
                stash includes: 'target/*.jar', name: 'myAppJar'
            }
        }

        stage('Run on Slaves') {
            parallel {
                stage('Server1') {
                    agent { label 'server1' }
                    tools { git 'LinuxGit' }
                    steps {
                        unstash 'myAppJar'
                        sh "java -cp target/my-app-1.0-SNAPSHOT.jar com.mycompany.app.App"
                    }
                }
                stage('Server2') {
                    agent { label 'server2' }
                    tools { git 'LinuxGit' }
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
