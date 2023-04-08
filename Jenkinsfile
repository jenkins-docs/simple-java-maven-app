pipeline {

    agent any
    
    stages {

        stage('K8S') {
            when {
            expressions {
                false
            }
        }
   
            steps {
            sh "kubectl apply -f deploy.yml"
            }
        }
    }
}
