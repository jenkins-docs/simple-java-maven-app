pipeline {

    agent any
    
    stages {

        stage('K8S') {
   
            steps {
            sh "kubectl apply -f deploy.yml"
            }
        }
    }
}
