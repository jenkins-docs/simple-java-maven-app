pipeline {
    // agent {
    //     docker {
    //         image 'maven:3-alpine' 
    //         args '-v /root/.m2:/root/.m2'
    //     }
    // }
    //
    agent any
    stages {
        stage('Build') { 
            steps {
                withDockerContainer(args: '-v /root/.m2:/root/.m2', image: 'maven:3.6.2-jdk-8-alpine', toolName: 'myDocker')
                {
                    sh 'mvn -B -DskipTests clean package' 
                }
                
            }
        }
        stage('Test') {
            steps {
                withDockerContainer(args: '-v /root/.m2:/root/.m2', image: 'maven:3-alpine', toolName: 'myDocker')
                {
                    sh 'mvn test'
                }
                
            }
            post {
                always {
                    archiveArtifacts artifacts: '**/*.jar', fingerprint: true
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('Sonar') {
            steps {
                withDockerContainer(args: '-v /root/.m2:/root/.m2', image: 'maven:3-alpine', toolName: 'myDocker')
                {
                    withSonarQubeEnv('sonar-nantes') { // If you have configured more than one global server connection, you can specify its name
                        sh 'mvn clean package sonar:sonar'
                    }
                }
            }
            
        }
        stage('Deliver') {
            steps {
                 withDockerContainer(args: '-v /root/.m2:/root/.m2', image: 'maven:3-alpine', toolName: 'myDocker')
                {
                    sh './jenkins/scripts/deliver.sh'
                }
            }
        }
    }
}