pipeline {
    agent any

    stages {
        stage('Clone application code') {
            steps {
                git 'https://github.com/durga97176/simple-java-maven-app.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn package -f pom.xml'
            }
        }
        stage('Build') {
            steps {
                echo 'mvn package -f pom.xml'
            }
        }
        stage('Deploy') {
            steps {
                echo 'deployed the application code'
    }
}