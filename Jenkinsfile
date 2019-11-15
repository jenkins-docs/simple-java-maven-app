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
                withDockerContainer(args: '-v /root/.m2:/root/.m2', image: 'maven:3-alpine', toolName: 'myDocker')
                sh 'mvn -B -DskipTests clean package' 
            }
        }
    }
}