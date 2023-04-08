pipeline {

    agent any
    
    stages {

        stage('K8S') {
            when {
            expression {
                false
            }
        }
   
            steps {
            sh "kubectl apply -f deploy.yml"
            }
        }
    }
}
