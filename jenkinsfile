pipeline {
    agent { dockerfile true }
    stages {
        stage('Test') {
            steps {
                bat 'node --version'
                bat 'svn --version'
            }
        }
    }
}
