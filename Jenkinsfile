pipeline {
  agent any
  tools {
    maven: 'maven'
  }
  stages {  
    stage('CheckouttheCode') {
      git branch: 'master', credentialsId: 'f5daaa2a-f3ea-4b03-80fe-a24f7c54b1fa', url: 'https://github.com/shareef242/simple-java-maven-app.git' 
    }
    stage('Build') {
      sh  "${mavenHome}/bin/mvn clean package"
    }
  }  
}
