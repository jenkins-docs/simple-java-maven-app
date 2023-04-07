pipeline {

    agent none
    
    stages {

        stage('test') {
        agent {
                label 'java-docker-slave1'
            }
   
            steps {

                echo "Test"
            }
        }
    }
}
