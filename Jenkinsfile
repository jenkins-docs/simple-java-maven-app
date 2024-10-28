pipeline {
    agent any
    tools {
        maven 'Maven 3.9.2' // This should match the name in Global Tool Configuration
    }
    stages {
        stage('Build') { 
            steps {
                sh 'mvn -B -DskipTests clean package' 
            }
        }
    }
}
