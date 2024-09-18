pipeline {

     agent {
          // label 'docker-agent-alpine'
          label 'ssh-agent'
         }
     
     options {
        buildDiscarder(logRotator(numToKeepStr: '3', artifactNumToKeepStr: '1'))
        timeout(time: 30, unit: 'MINUTES')
     }

    environment {
        // pendiente sonarq
        ANALYSIS_SONARQUBE = "true"
        ANALYSIS_OWASP = "false"
        SONARQUBE_SCANNER_HOME = tool 'sonarqube'
        PROJECT_NAME = "rga_tasks"
        PROJECT_CLIENT = "rga"
        SONARQUBE_TAG="1.0"
        SONARQUBE_CONFIG_FILE_PATH = "./sonar-project.properties"
    }

    tools{
         maven 'maven_3.9.9'
        }    
    
    stages {

        stage('Checkout') {
                steps {
		   script {
	                git credentialsId: 'git-credentials', poll: false, 
	                url: 'https://github.com/argos-iot/simple-java-maven-app.git'
	                def version = sh script: 'mvn help:evaluate -Dexpression=project.version -q -DforceStdout', returnStdout: true
	                def artifactId = sh script: 'mvn help:evaluate -Dexpression=project.artifactId -q -DforceStdout', returnStdout: true
	                echo "Building version: $version, artifactID: $artifactId"
                }
	    }
        }

        stage('Sonarqube') {

            steps {
              withSonarQubeEnv(credentialsId: 'sonar-jenkins', installationName: 'sonarqube') {
                  // sh "${SONARQUBE_SCANNER_HOME}/bin/sonar-scanner -Dproject.settings=${env.SONARQUBE_CONFIG_FILE_PATH}"
		  sh "mvn clean verify sonar:sonar -Dsonar.projectKey=simple-mvn-test -Dsonar.java.binaries=target/classes"
              	}	
            }
	}
   
        stage('Checking Quality Gate') {
            steps {
                script {
                    timeout(time: 5, unit: 'MINUTES') {
                        def qualityGate = waitForQualityGate()
                        if (qualityGate.status != 'OK') {
                            error "Quality Gate KOOO: ${qualityGate.status}"
                        }
                    }
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
                
                dependencyCheckPublisher pattern: '**/dependency-check-report.xml'
             }
        }
	    
        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Run Unit Tests') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    // Publicar el reporte de las pruebas unitarias usando JUnit
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
  
        stage('Packaging') { 
            steps {
                sh "echo ***************** Clean Package"
		sh "mvn -DskipTests=true -Denforcer.skip=true clean package"
		sh "echo *****************"
            }
        }

 // 	  stage('Jacoco Coverage') {
	//      steps {
	// 	 sh 'mvn jacoco:report'
	//      }
	// }
    }

    post {

        // always {
        //     script {
        //         echo  "Changelog, 10 commits"
        //         def changeLogText = ""
        //         def changeSet = currentBuild.changeSets

        //         for (int i = 0; i < changeSet.size(); i++) {
        //             def entries = changeSet[i].items
        //             for (int j = 0; j < entries.length && j < 10; j++) {
        //                 def entry = entries[j]
        //                 changeLogText += "Commit ${j+1}:\n"
        //                 changeLogText += "Autor: ${entry.author}\n"
        //                 changeLogText += "Mensaje: ${entry.msg}\n"
        //                 changeLogText += "Fecha: ${entry.timestamp}\n"
        //                 changeLogText += "---------------------------------------------\n"
        //             }
        //         }

        //         // Guardar el changelog en un archivo
        //         writeFile file: 'changelog.txt', text: changeLogText
        //         archiveArtifacts artifacts: 'changelog.txt', allowEmptyArchive: false
        //     }
        // }
	    
        success {
            script {

                jacoco(
                            execPattern: '**/build/jacoco/*.exec',
                            classPattern: '**/build/classes/java/main',
                            sourcePattern: '**/src/main'
                        )

                archiveArtifacts artifacts: '**/*.jar,**/*.war,target/surefire-reports/*.xml',
                    allowEmptyArchive: true,
                    fingerprint: true,
                    onlyIfSuccessful: true
            }
         }
        // cleanup {
        //     cleanWs(cleanWhenNotBuilt: false,
        //             deleteDirs: true,
        //             disableDeferredWipeout: true,
        //             notFailBuild: true)
        // }
	    
    }
}
