pipeline {
    agent any

    environment {
        MAVEN_HOME = tool name: 'Maven 3.9.9', type: 'maven'
    }

    stages {
        stage('Build') {
            steps {
                withEnv(["PATH+MAVEN=${MAVEN_HOME}/bin"]) {
                    bat 'mvn -B -DskipTests clean package'
                }
            }
        }

        stage('Test') {
            steps {
                withEnv(["PATH+MAVEN=${MAVEN_HOME}/bin"]) {
                    bat 'mvn test'
                }
            }
        }

        stage('Run') {
            steps {
                bat 'java -jar target/my-app-1.0-SNAPSHOT.jar'
            }
        }
    }
}