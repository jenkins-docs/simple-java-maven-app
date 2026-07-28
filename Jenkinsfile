pipeline {
    agent any

    stages {
        stage('Test') {
            steps {
                echo 'Hello from Jenkins!'
                sh 'echo "This is a test build."'
                sh 'pwd'
                sh 'ls -la'
            }
        }
    }

    post {
        always {
            echo 'Pipeline completed.'
        }
    }
}
