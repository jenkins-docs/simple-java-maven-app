pipeline {
    agent none
    stages {
        stage('Build on Master') {
            agent { label 'built-in' }
            tools { maven 'MAVEN' }
            steps {
                // Only build if source is already available; skip checkout on master
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
                        git url: 'https://github.com/pavan203/simple-java-maven-app.git', branch: 'master'
                        unstash 'myAppJar'
                        sh "java -cp target/my-app-1.0-SNAPSHOT.jar com.mycompany.app.App"
                    }
                }
                stage('Server2') {
                    agent { label 'server2' }
                    tools { git 'LinuxGit' }
                    steps {
                        git url: 'https://github.com/pavan203/simple-java-maven-app.git', branch: 'master'
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
