pipeline{
    agent{
        label 'agentx'
    }
     tools {
        maven 'maven1' 
    }
    options {
      buildDiscarder logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '5', numToKeepStr: '5')
       timestamps
       timeout(5)

    } 


    stages{
        stage('checkout'){
            steps{
            checkout scm
            }
        }
        stage('build'){
            steps{
                sh 'mvn --version'
                sh 'mvn clean install -Dskiptests'
            }
        }
        stage('Test'){
            steps{
            sh "mvn test"
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*xml'
            }
        
        }
        stage('post-build'){
            steps{
                echo "this is post build message ro demonstrate the badge plugin "
            }
        }
        
    }
}
