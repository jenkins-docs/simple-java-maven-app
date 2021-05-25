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
                sh 'mvn clean install -Dskiptests'
            }
        }
        stage('test'){
            steps{
                 echo '################### lets do the  testing  #####################'
                sh 'mvn test'
                junit allowEmptyResults: true, testResults: 'target/test-reports/*.xml'
            }
        }
        stage('post build'){
            steps{
                echo "this is post  build"
            }
        }
    }
}
