pipeline{
    agent {
        label 'agentx'
    }
    stages{
        stage('build'){
            steps{
                sh 'mvn clean install'
            }
        }
        stage('post build'){
            steps{
                echo "this is post  build"
            }
        }
    }
}
