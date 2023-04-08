pipeline {

    agent any
    
    stages {

        stage('test') {
   
            steps {

                sh "kubectl get pods --kubeconfig /admin.conf"
            }
        }
    }
}
