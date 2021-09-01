pipeline {
    agent any
    environment {
        PATH = " C:\Users\rmalewad\Downloads\apache-maven-3.8.1-bin\apache-maven-3.8.1\bin:$PATH"
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
            }
        }
    }

}
