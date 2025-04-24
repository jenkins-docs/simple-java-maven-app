pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                script {
                    // Clean and build the Maven project
                    sh 'mvn clean package'
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    // Deploy the WAR file to Tomcat
                    sh 'curl --upload-file target/java-maven-tomcat-app.war "http://localhost:8080/manager/text/deploy?path=/java-maven-tomcat-app&update=true" --user admin:password'
                }
            }
        }
    }

    post {
        success {
            echo 'Deployment successful!'
        }
        failure {
            echo 'Deployment failed.'
        }
    }
}