pipeline {
    agent none
    stages {
        stage('Build on Master') {
            agent { label 'built-in' }
            tools { maven 'MAVEN' }
            steps {
                // Only build if source is already available; skip checkout on master
                bat "mvn clean package"
            }
        }

        stage('Run on Slaves') {
            parallel {
                stage('Server1') {
                    agent { label 'server1' }
                    tools { git 'LinuxGit' }
                    steps {
                            sh 'mvn package -DskipTests'
                        }
                
                    post {
                        success {
                            archiveArtifacts 'target/*.jar'
                            echo 'Package created successfully!'
                        }
                    }
                }
                stage('Server2') {
                    agent { label 'server2' }
                    tools { git 'LinuxGit' }
                    steps {
                            sh 'mvn package -DskipTests'
                        }
                
                    post {
                        success {
                            archiveArtifacts 'target/*.jar'
                            echo 'Package created successfully!'
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
