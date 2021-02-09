pipeline {
    agent any
    stages {
        stage('Cleaning-stage') {
            steps {
                echo 'i am in Cleaning stage of pipeline'
                sh 'mvn clean'
                echo "This is my commit id ${env.GIT_COMMIT}"
            }
        }
        stage('testing-stage') {
            when {
                expression {
                    return env.BRANCH_NAME == 'master';
                }
            }
            steps {
                echo "This is test phase of my build job"
                sh 'mvn test'
                echo "Running ${env.BUILD_ID} on ${env.JENKINS_URL} and job name is ${env.JOB_NAME}"
                
            }
        }
        stage('Building-package') {
            steps {
                echo "Now I am in package phase"
                sh 'mvn package'
            }
        }

    }
    post {
        always {
            echo "Run always"
    }
 }
}