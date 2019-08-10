pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v /root/.m2:/root/.m2'
        }
    }

    stages {
        stage('scm') {
            steps {
                echo 'Getting from SCM..'
                checkout([$class: 'GitSCM', 
                          branches: [[name: '${sha1}']], 
                          doGenerateSubmoduleConfigurations: false, 
                          extensions: [], 
                          submoduleCfg: [], 
                          userRemoteConfigs: 
                          [[credentialsId: 'git_account', 
                            url: 'https://github.com/mryongsim/simple-java-maven-app.git',
                            refspec: '+refs/pull/*:refs/remotes/origin/pr/*']]])
                          
            }
        }
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('SonarQube analysis') {
            steps {
               script {
                   scannerHome = tool 'SonarQube Scanner 4.0'
               }
               withSonarQubeEnv('sonar'){
                   sh "${scannerHome}/bin/sonar-scanner -X"
               }
            }
        }
    }
}
