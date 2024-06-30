pipeline {

agent none
  stages {

      stage('SCM') {
        agent { label 'build-node' }
        steps {
           echo 'Pulling Source code from GitHubRepo'
           git 'https://github.com/yvpatel/simple-java-maven-app.git'
         }
      }
      stage('build') {
        agent { label 'build-node' }
        steps {
           echo "code fetch from gitrepo and generating artifect using maven"
           sh 'mvn clean package'
         }
      }
      stage('Deployed') {
        agent { label 'build-node' }
        steps {
           echo "code is deployed to Production"
           sh 'java -jar target/*.jar'
         }

     }

      stage('Test') {
        agent { label 'build-node' }
        steps {
           echo "code is working well"

         }

     }

}

}
