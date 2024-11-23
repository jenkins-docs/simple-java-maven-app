pipeline {
    agent any

    environment {
        MAVEN_HOME = tool name: 'Maven 3.9.9', type: 'maven'
    }

    stages {
        stage('Build') {
            steps {
                env.PATH = "${MAVEN_HOME}/bin:${env.PATH}"
                bat 'mvn -B -DskipTests clean package'
            }
        }
    }
}