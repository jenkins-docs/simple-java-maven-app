pipeline {
     options {
      buildDiscarder(logRotator(numToKeepStr: '3', artifactNumToKeepStr: '1'))
      timeout(time: 10, unit: 'MINUTES')
     }

    environment {
        SONARQUBE_SCANNER_HOME = tool 'sonarqube'
        ANALYSIS_SONARQUBE = "true"
        ANALYSIS_OWASP = "true"
    }
    
    agent {
        label 'docker-agent-alpine'
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
                    publishHTML([allowMissing: false, 
                             alwaysLinkToLastBuild: false,
                             keepAll: false, 
                             reportDir: 'target/surefire-reports',
                             reportFiles: 'TEST-com.mycompany.app.AppTest.xml', 
                             reportName: 'surefire-reports',
                             reportTitles: 'surefire-reports', 
                             useWrapperFileDirectly: true])
                }
            }
        }

        stage('OWASP Dependency-Check Vulnerabilities') {
            when { environment name: 'ANALYSIS_OWASP', value: 'true' }
              steps {
                dependencyCheck additionalArguments: ''' 
                            -o './'
                            -s './'
                            -f 'ALL' 
                            --prettyPrint''', odcInstallation: 'dependency-check'
                
                dependencyCheckPublisher pattern: 'dependency-check-report.xml'

                // publishHTML([allowMissing: false, 
                //              alwaysLinkToLastBuild: false,
                //              keepAll: false, 
                //              reportDir: '',
                //              reportFiles: 'dependency-check-report.html', 
                //              reportName: 'HTML Report',
                //              reportTitles: 'dependency-check', 
                //              useWrapperFileDirectly: true])
              }
         }
    }

    

    post {
        success {
            sh "ls -lR target"
            archiveArtifacts artifacts: '**/*.jar,**/*.war,target/surefire-reports/*.xml',
                   allowEmptyArchive: true,
                   fingerprint: true,
                   onlyIfSuccessful: true
        }

        cleanup {
            cleanWs(cleanWhenNotBuilt: false,
                    deleteDirs: true,
                    disableDeferredWipeout: true,
                    notFailBuild: true)
        }
    }
}
