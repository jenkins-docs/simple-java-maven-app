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
                    steps {
                        sh 'mvn package -DskipTests'
                    }

                }
                stage('Server2') {
                    agent { label 'server2' }
                    steps {
                        sh 'mvn package -DskipTests'
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
