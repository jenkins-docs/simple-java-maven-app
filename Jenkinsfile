pipeline {
  agent any
    
  stages {

    stage('Build') {
          steps {
            sh 'mvn -B -DskipTests clean package'
           }
      /*
          post {
              success {
                  slackSend channel: 'rally-jenkins-integration', color: '#00ff00', message: "BUILD SUCCESSFUL\nJob Name = ${env.JOB_NAME}\nJob Number = ${env.BUILD_NUMBER}\nJob URL = ${env.BUILD_URL} ",
                    teamDomain: 'rallydemo', tokenCredentialId: 'JenkinsSlack'
                }
              failure {
                  slackSend channel: 'rally-jenkins-integration', color: '#00ff00', message: "BUILD UNSUCCESSFUL\nJob Name = ${env.JOB_NAME}\nJob Number = ${env.BUILD_NUMBER}\nJob URL = ${env.BUILD_URL} ",
                    teamDomain: 'rallydemo', tokenCredentialId: 'JenkinsSlack'
               }
          }
       */
    }
    
    stage('Test') {
      steps {
        sh 'mvn test'
       }
        post {
            always {
              junit 'target/surefire-reports/**/*.xml'
            }
            success {
               slackSend channel: 'rally-jenkins-integration', color: '#00ff00', message: "BUILD SUCCESSFUL\nJob Name = ${env.JOB_NAME}\nJob Number = ${env.BUILD_NUMBER}\nJob URL = ${env.BUILD_URL} ",
                      teamDomain: 'rallydemo', tokenCredentialId: 'JenkinsSlack'
             }
            failure {
                slackSend channel: 'rally-jenkins-integration', color: '#00ff00', message: "BUILD UNSUCCESSFUL\nJob Name = ${env.JOB_NAME}\nJob Number = ${env.BUILD_NUMBER}\nJob URL = ${env.BUILD_URL} ",
                      teamDomain: 'rallydemo', tokenCredentialId: 'JenkinsSlack'
            }
         }
     }
    
    stage('Deploy') {
      
      steps {
        echo "git branch = ${env.GIT_BRANCH}"
        echo "git local branch = ${env.GIT_LOCAL_BRANCH}"
        script {
          if (env.GIT_BRANCH == 'origin/master') {
              echo 'master branch deployed'
          } else {
              echo 'PR only'
          }
        }
       }
        
     }
      
  }
}
