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
                bat "mvn clean" 
                bat "mvn install"
                bat "mvn test"
                bat "mvn package"
                echo 'build successfull !'
            }
        }
    }

}
