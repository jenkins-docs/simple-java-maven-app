pipeline {
    agent any

    parameters {
        choice(name: 'ENVIRONMENT', choices: ['dev', 'qa', 'prod'], description: 'Target environment')
        choice(name: 'BUILD_TYPE', choices: ['clean', 'install', 'package'], description: 'Maven build goal')
    }

    tools {
        maven 'Maven_3.8.6' // Name from Jenkins global tool config
    }

    environment {
        MAVEN_OPTS = "-Dmaven.test.failure.ignore=true"
    }

    stages {
        stage('Checkout') {
            steps {
                git url: ‘https://github.com/jenkins-docs/simple-java-maven-app.git’
            }
        }

        stage('Build') {
            steps {
                sh "mvn ${params.BUILD_TYPE} -Denv=${params.ENVIRONMENT}"
            }
        }

        stage('Post-Build') {
            steps {
                echo "Build completed for ${params.ENVIRONMENT} using goal ${params.BUILD_TYPE}"
            }
        }
    }

    post {
        success {
            echo 'Pipeline executed successfully!'
        }
        failure {
            echo 'Pipeline failed. Check logs for details.'
        }
    }
}
