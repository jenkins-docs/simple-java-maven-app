pipeline {
    agent {
        label 'master'
    }

    tools {
          jdk 'OpenJDK-17.0.7-LTS-AArch46'
          maven 'Maven-3.9.2'
    }

    stages {
        stage('Build') {
            steps {
                sh "mvn -Dmaven.test.failure.ignore=true clean package"
            }

            post {
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
        stage('SonarQube') {
            steps {
                withSonarQubeEnv('Calypso Binar SonarQube Server') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
        stage('Docker build') {
            steps {
                sh "docker build . -t hello-world-spring"
            }
        }
        stage('Docker run') {
            when {
                branch "master"
            }
            steps {
                sh "docker stop hello-world-spring || true"
                sh "docker run -it -d --rm -p 8081:8080 --name hello-world-spring hello-world-spring"
            }
        }
    }
}
