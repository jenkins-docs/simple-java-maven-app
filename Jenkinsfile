@Library("shared-library") _
pipeline {
    agent any
    tools {
        maven 'maven 3.8.2'
    }

    stages {
        stage('verify git') {
            steps {
                echo "$GIT_BRANCH"
            }
        }
        stage ('build') {
            steps{
               buildMaven()
            }
        }
        stage('sonarqube analysis') {
            
            withSonarQubeEnv('sonar-localost') {
	            bat "sonar:sonar"
          }
       }
    }

}
