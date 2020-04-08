pipeline {
   stages {
        stage('Pull') {
            steps{
                //拉取代码
            	git 'https://https://github.com/yjj2100/simple-java-maven-app-1.git'
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
