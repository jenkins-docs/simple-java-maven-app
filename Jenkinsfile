pipeline {
    agent {
        docker {
            image 'maven:3-alpine' 
            args '-v /root/.m2:/root/.m2' 
        }
    }
   stages {
        stage('Pull') {
            steps{
                //拉取代码
            	git 'https://github.com/yjj2100/simple-java-maven-app-1.git'
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
