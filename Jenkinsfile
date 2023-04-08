pipeline {

    agent any
    
    stages {

        stage('test') {
   
            steps {

                sh "kubectl get pods --kubeconfig /root/admin.config"
            }
        }
    }
}
