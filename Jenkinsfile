pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v /root/.m2:/root/.m2'
        }
    }
    stages {
        stage('Build') {
            steps {
                echo EKKLOT1
                sh 'mvn -B -DskipTests clean package'
            }
        }
    }
}