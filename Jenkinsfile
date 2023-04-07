pipeline {

    agent none
    
    stages {

        stage('gitscm') {
        agent {
                label 'java-docker-slave1'
            }
   
            steps {

                echo "Master Branch"
            }
        }
    }
}
