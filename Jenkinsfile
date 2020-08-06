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
                sh 'hello yujin' 
                sh 'mvn -B -gs maven-setting.xml -DskipTests clean package' 
            }
        }
    }
}
