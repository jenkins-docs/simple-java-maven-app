pipeline {
//    agent { docker { image 'maven:3.3.3' } }
    agent any
    stages {
        stage('build') {
            steps {
                sh 'mvn --version'
                sh 'echo Build'
            }
        }
        stage('stage') {
            steps {
//                sh 'echo "Hello World"'
                sh 'echo Stage'
                sh '''
                    echo "Multiline shell steps works too"
                    ls -lah
                '''
            }
        }
    }
}
