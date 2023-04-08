pipeline {

    agent any
    
    stages {

        stage('test') {
   
            steps {
            kubernetesDeploy(configs: "deploy.yml" , kubeconfigID: "K8S")
            }
        }
    }
}
