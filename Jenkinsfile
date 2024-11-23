pipeline {
    agent any

    environment {
        MAVEN_HOME = tool name: 'Maven 3.9.9', type: 'maven'
        PATH = "${MAVEN_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Build') {
            steps {
                bat 'mvn -B -DskipTests clean package'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Run') {
            steps {
                bat 'java -jar target/my-app-1.0-SNAPSHOT.jar'
            }
        }
    }
}