import jenkins.model.*
pipeline {
  agent any 
    environment {
		APP="simple-java-maven-app"
		GITREPO="JFLIT"
		GIT_BRANCH="${params.GIT_BRANCH}"
        ORG= "JFLIT"
		//ORG="${params.ORG}"
		//BUILD_DIR="/opt/play-app"
		RELEASE_VERSION="${params.RELEASE_VERSION}"
		RELEASE_DATE=new java.text.SimpleDateFormat('yyyyMMdd').format(new Date())
		CACHE="${env.CACHE_DIR}/jubliant"
		ATR_ORG="" //need info artifactory
		APP_VERSION="${APP}.version.${BUILD_NUMBER}"
		sources="src/main"
		java_binaries="target"		
		ENABLE_PROJECT_FOLDER="${params.ENABLE_PROJECT_FOLDER}"
		SONARQUBE_SCANS="${params.SONARQUBE_SCANS}"
		FORTIFY_SCANS="${params.FORTIFY_SCANS}"
		branch_name="${params.GIT_BRANCH}"
		
   }
    
   parameters {
    	      string(name: 'APP', description: 'App name')
            booleanParam(name: 'ENABLE_PROJECT_FOLDER', defaultValue: false, description: 'Please uncheck if not want to push artifacts- to Environment Specific folder')
            string(name: 'GIT_BRANCH', defaultValue: 'dev', description: 'Git branch')
            string(name: 'ORG', defaultValue: 'JFLIT', description: 'Git org')
            string(name: 'DOCKER_TAG', description: 'docker tag')
            string(name: 'JAVA_OPTION', description: "JAVA Compiler")
            string(name: 'DEPLOYMENT_GIT_BRANCH', description: "DEPLOYMENT GIT BRANCH")
            booleanParam(name: 'SONARQUBE_SCANS', defaultValue: false, description: "sonarqube scans")
            booleanParam(name: 'FORTIFY_SCANS', defaultValue: false, description: "fortify scans")  
		 booleanParam(name: 'SKIP_DEPLOY', defaultValue: false, description: "Skip Deploy") 
		
	       }
	
   stages {
   	 stage('Cleanup') {
      		steps {
        	ansiColor('xterm') {
          	deleteDir()
        	}
          }
      }
	  
	  
	  stage('Checkout') {
      		steps {
        		ansiColor('xterm') {
			script {
// Display current build information on Jenkins
			          //currentBuild.displayName = "#${env.BUILD_NUMBER} ${params.APP}:${DEPLOYMENT_MODE}"
				   currentBuild.displayName = "#${env.BUILD_NUMBER} ${params.APP}"
               			   currentBuild.description = "from ${params.GIT_BRANCH}"
          		deleteDir()		      
          		checkout scm
// Checkout app code (OOM)
          		checkout([
               		$class: 'GitSCM',
            		branches: [[ name: "${env.GIT_BRANCH}" ]],
            		doGenerateSubmoduleConfigurations: false,
            		userRemoteConfigs: [[credentialsId: 'Git-access', url: "https://github.com/raghugitty/simple-java-maven-app.git"]],
            		extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: "app"]]
          		])

            }
                }
            }
        }
      stage('Build'){
			steps {
			ansiColor('xterm') {
			sh "mkdir -p ${env.CACHE}/mvn"
			script {
			def server = Artifactory.server('Artifactory')
		
			if("${params.JAVA_OPTION}" == "openjdk8") {
         		env.buildmachine_tag = "v2"
            	} else if("${params.JAVA_OPTION}" == "openjdk11") {
              	env.buildmachine_tag = "openjdk11.0.8"
            	} else {
			    env.buildmachine_tag = "testing"
			    }
            		docker.withRegistry('https://hub.docker.com/repository/docker/magalam87/docker-private', 'DTR'){
              	docker.image("magalam87/docker-private:docker-slave").inside("-v ${env.CACHE}:${env.WORKSPACE}/cache") {			
			          withCredentials([usernamePassword(credentialsId: 'jenkins-agent', passwordVariable: 'credsfilevar', usernameVariable: 'username')]) {
            		//dir("common-framework"){
			        //sh "ls -l"					
                    //sh "mvn clean install"
                    //sh "mkdir -p ${env.WORKSPACE}/m2"
                    //sh "pwd"
                    //sh "mvn -Dmaven.repo.local=${env.WORKSPACE}/cache/m2 clean install" 

								    //       }
                   
                    dir("app"){

                    sh "mvn clean install"	
		            sh "mvn -Dmaven.repo.local=${env.WORKSPACE}/cache/m2 clean install"
					sh "ls -l "
                 		           }


				
							}	
						}		
					}			
				}				
				}					
			}						
			}		   

    
   }

}
