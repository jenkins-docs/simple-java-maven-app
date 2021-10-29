pipeline {
      agent any
   tools{
       maven "M3"
   }
      stages {
        stage('Build') {
          steps {
            echo 'Building...'
                            sh 'mvn clean verify' 

//                             sh 'mvn clean verify -e sonar:sonar -Dsonar.projectKey=demoappkey -Dsonar.host.url=http://192.168.1.211:9000 -Dsonar.login=03ed6c7ad26a26157b66e49266bb1b636fddec42' 
          }
        }
  stage('Code Quality Check via SonarQube') {
   steps {
       script {
           withSonarQubeEnv("SQS3") {
           sh "mvn clean package sonar:sonar" 
//           -Dsonar.projectKey=demoappkey \
//          -Dsonar.sources=. \
//          -Dsonar.css.node=. \
//          -Dsonar.host.url=http://192.168.1.211:9000 \
//           -Dsonar.login=03ed6c7ad26a26157b66e49266bb1b636fddec42"
               }
           }
       }
   }
        stage('SNYK') {
          steps {
            echo 'Testing...'
            snykSecurity(
              snykInstallation: "SNYK_LATEST",
              snykTokenId: "SNYKAPI",
              // place other parameters here
            )
          }
        }
        stage('Deploy') {
          steps {
            echo 'Deploying...'
          }
        }
      }
    }