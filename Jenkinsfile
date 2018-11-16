pipeline {
    agent any
    tools {
        maven 'mvn'
    }
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
            post {
                success {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
    }
}
