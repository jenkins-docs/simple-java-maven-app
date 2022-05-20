pipeline {
    agent any
    tools {
        maven 'maven'
    }
    stages {
        stage('Initialize'){
            steps{
                echo "PATH = ${M2_HOME}/bin:${PATH}"
                echo "M2_HOME = /opt/apache-maven-3.8.5"
            }
        }
    	stage ('SCM Checkout') {
      	    steps {
            // Get source code from Gitlab repository
        	checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: '']], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'github-access-jenkins', url: 'https://github.com/kumar646464/simple-java-maven-app.git']]])
	    }
    	}
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
    }
}
