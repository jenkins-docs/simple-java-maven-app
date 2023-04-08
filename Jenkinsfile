pipeline {

    agent any
    parameters {
        booleanParam(name: "isDeployPod" , defaultValue: true)
    }
    
    stages {

        stage('K8S') {
            when {
            expression {
                params.isDeployPod
            }
        }
   
            steps {
            sh "kubectl apply -f deploy.yml"
            }
        }
    }
    post {
        success {
            echo "all good"
        }
        
        failure {
            echo "failed...."
        }
        
        always {
            echo "always"
        }
    }
}
