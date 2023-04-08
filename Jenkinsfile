pipeline {

    agent any
    
    stages {

        stage('test') {
   
            steps {

                sh "kubectl get pods"
                 sh "kubectl apply -f deploy.yml"
            }
        }
    }
}
