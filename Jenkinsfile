pipeline {
    agent any
    tools {
        maven 'maven'
    }
    stages {
        stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}/bin/mvn"
                '''
            }
        }
        stage('SCM Checkout') {
            steps {
            // Get source code from Gitlab repository
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: '']], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'f5daaa2a-f3ea-4b03-80fe-a24f7c54b1fa', url: 'https://github.com/shareef242/simple-java-maven-app.git']]])
            }
        }
        stage('Mvn Package') {
            steps {
                sh 'mvn -B -DskipTests clean package -e'
            }
        }      
    }
}
