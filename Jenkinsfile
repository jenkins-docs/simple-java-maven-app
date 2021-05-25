pipeline{
    agent {
        label 'agentx'
    }
    tools {
     maven 'maven1'
    }     
    options {
    buildDiscarder logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '5', numToKeepStr: '5')
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
                junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
            }
        }
        stage('post build'){
            steps{
                echo "this is post  build"
            }
        }

    }
    post{
        always{
            echo "this will execute always"
            deleteDir()
        }
        failure{
            echo "sending mail to the concerned person"
        }
        success{
            echo "always got successfull"
        }
    }



}
