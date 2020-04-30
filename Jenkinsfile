pipeline {
    agent {
        docker {
            // downloads the maven:3-alpine Docker image (if it’s not already available on your machine)
            image 'maven:3-alpine'
            args '-v /root/.m2:/root/.m2'
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
    }
}