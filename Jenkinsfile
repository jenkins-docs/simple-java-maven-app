try {
node ('master') {
    stage('Checkout') {
        echo "checkout code from github"
        checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/sushmabn/simple-java-maven-app.git']]])
        echo "checked "
    }
    
    stage('Build') {
        echo "build the maven code"
        
    }
    stage('Build status') {
        
        echo "Initial result: ${currentBuild.result}"
        echo "currentResult: ${currentBuild.currentResult}"
        
    }
    stage('Post Build') {
                always {
                    echo "Post-Initial result: ${currentBuild.result}"
                    echo "Post-Initial currentResult: ${currentBuild.currentResult}"
                }
    }
   

    stage('Building SONAR ...') {
        sh 'mvn clean sonarqube'
        }
     
    catch (e) {
        emailext attachLog: true, body: 'See attached log', subject: 'BUSINESS Build Failure', to: 'abc@gmail.com'
    step([$class: 'WsCleanup'])
    return
    }
}
