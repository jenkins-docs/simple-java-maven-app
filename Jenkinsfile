pipeline {

     agent {
          // label 'docker-agent-alpine'
          label 'ssh-agent'
         }
     
     options {
        buildDiscarder(logRotator(numToKeepStr: '3', artifactNumToKeepStr: '1'))
        timeout(time: 10, unit: 'MINUTES')
     }

    environment {
        // pendiente sonarq
        ANALYSIS_SONARQUBE = "true"
        ANALYSIS_OWASP = "false"
    }

    tools{
         maven 'maven_3.9.9'
        }    
    
    stages {
        stage('Build') { 
            steps {
                sh "echo start building with mvn, skipping test"
                sh "mvn -B -DskipTests -Denforcer.skip=true clean package"
            }
        }

         stage('Unit Test') {
            steps {
                script {
                    sh 'echo Running test'
                    sh "mvn test"
                     sh "ls -laR target"
                    publishHTML([allowMissing: false, 
                             alwaysLinkToLastBuild: false,
                             keepAll: false, 
                             reportDir: 'target/surefire-reports',
                             reportFiles: 'com.mycompany.app.AppTest.txt', 
                             reportName: 'surefire-reports',
                             reportTitles: 'surefire-reports', 
                             useWrapperFileDirectly: true])
                }
            }
        }

        stage('OWASP Dependency-Check Vulnerabilities') {
            when {
                 environment name: 'ANALYSIS_OWASP', value: 'true'
            }
              steps {
                dependencyCheck additionalArguments: ''' 
                            -o './'
                            -s './'
                            -f 'ALL' 
                            --prettyPrint''', odcInstallation: 'dependency-check'
                
                dependencyCheckPublisher pattern: 'dependency-check-report.xml'

                publishHTML([allowMissing: false, 
                             alwaysLinkToLastBuild: false,
                             keepAll: false, 
                             reportDir: '',
                             reportFiles: 'dependency-check-report.html', 
                             reportName: 'Dependency Check',
                             reportTitles: 'dependency-check', 
                             useWrapperFileDirectly: true])
              }
         }
    }

    post {
        success {
            archiveArtifacts artifacts: '**/*.jar,**/*.war,target/surefire-reports/*.xml',
                   allowEmptyArchive: true,
                   fingerprint: true,
                   onlyIfSuccessful: true
          }
         
        // cleanup {
        //     cleanWs(cleanWhenNotBuilt: false,
        //             deleteDirs: true,
        //             disableDeferredWipeout: true,
        //             notFailBuild: true)
        // }
    }
}
