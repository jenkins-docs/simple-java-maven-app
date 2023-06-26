pipeline {
    agent any

    tools {
          jdk 'OpenJDK-17.0.7-LTS-AArch46'
          maven 'Maven-3.9.2'
    }

    stages {
        stage('Build') {
            steps {
                sh "echo $PATH"
                sh "java --version"
                sh "mvn -Dmaven.test.failure.ignore=true clean package"
            }

            post {
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
    }
}
