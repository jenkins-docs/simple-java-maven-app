pipeline{
    agent {
        label 'agentx'
    }
    tools {
     maven 'maven1'
    }      
  stages{
        stage('build'){
            steps{
                sh 'mvn -version'
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
