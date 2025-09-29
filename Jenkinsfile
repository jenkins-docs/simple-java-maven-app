pipeline {
    agent any

    tools {
        maven 'M3'
    }

    stages {
        stage('Checkout') {
            steps {
                // FIXED: Placed 'branch' parameter on the same line
                git url: 'https://github.com/rajnish107/simple-java-maven-app.git', branch: 'master'
            }
        }

        stage('Build') {
            steps {
                sh "mvn clean package"
            }
        }

        stage('Test') {
            steps {
                sh "mvn test"
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying to tomcat...'
                // FIXED: Added closing parenthesis here
                sshagent(credentials: ['tomcat-credentials']) {
                    // FIXED: Changed 'ssh' to 'sh'
                    sh """
                        scp -o StrictHostKeyChecking=no \
                            target/maven-web-app.war \
                            ec2-user@52.91.249.201:/usr/share/tomcat10/webapps/
                    """
                }
            }
        }
    }

    post {
        always {
            echo 'pipeline finished'
        }

        success {
            echo 'pipeline succeeded'
        }

        failure {
            echo 'pipeline failed'
        }
    }
}
