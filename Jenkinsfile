pipeline {
agent none
    stages {

           stage('SCM') {
              agent { label 'java-maven' }
              steps {
                   echo "pulling source code from githubrepo"
                   git 'https://github.com/Abhay93280/simple-java-maven-app.git'
                }
           }
           stage('build') {
              agent { label 'java-maven' }
              steps {
                   echo "code code fatch from gitrepo and generating artifect using maven"
                   sh 'mvn clean package'
                }
           }
           stage('deployed') {
              agent { label 'java-maven' }
              steps {
                   echo "code is deployed to production"
                   sh 'java -jar target/*.jar'
              }
           }
           stage('test') {
              agent { label 'java-maven' }
              steps {
                   echo "code is working well"
                   
              }
           }
}
}
