pipeline {
    agent {
        docker {
            image 'maven:3-alpine' 
            args '-v /root/.m2:/root/.m2' 
        }
    }
    environment {
        RUNNING = 'I am running'
        SCRIPT_NAME = 'deliver.sh'
    }
    stages {
        stage('Build') { 
            steps {
                echo "${env.RUNNING}"
                sh 'mvn -B -DskipTests clean package' 
            }
        }
	stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('Deliver for master') {
            when {
                branch 'master'
            }
            steps {
                echo "${env.RUNNING} ${env.SCRIPT_NAME}"
                sh './jenkins/scripts/deliver.sh' 
            }
        }
        stage('Deliver for development') {
            when {
                branch 'development'
            }
            environment {
                SCRIPT_NAME = 'deliver-for-development.sh'
            }
            steps {
                echo "${env.RUNNING} ${env.SCRIPT_NAME}"
                sh "./jenkins/scripts/${env.SCRIPT_NAME}"
                input message: 'Finished using the web site? (Click "Proceed" to continue)'
                sh './jenkins/scripts/kill.sh'
            }
        }
        stage('Deploy for production') {
            when {
                branch 'production'
            }
            environment {
                SCRIPT_NAME = 'deliver-for-production.sh'
            }
            steps {
                echo "${env.RUNNING} ${env.SCRIPT_NAME}"
                sh "./jenkins/scripts/${env.SCRIPT_NAME}"
                input message: 'Finished using the web site? (Click "Proceed" to continue)'
                sh './jenkins/scripts/kill.sh'
            }
        }
    }
}
