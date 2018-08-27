node {
    agent none
    stages {
        stage('Build') { 
            steps {
                sh 'mvn -B -DskipTests clean package' 
                script {
                    def customImage = docker.build("image_of_justice")
                }
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('Deliver') {
            steps {
                sh './jenkins/scripts/deliver.sh'
            }
        }
    }
}