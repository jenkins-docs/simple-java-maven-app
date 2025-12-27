def call(Map config) {
    // Requirement: Apply naming conventions
    def fullJobName = "PROD-${config.appName}-${env.BUILD_NUMBER}"

    pipeline {
        agent any
        stages {
            stage('Checkout') {
                steps {
                    echo "Standardized Checkout for ${fullJobName}"
                    git url: config.repoUrl, branch: 'final'
                }
            }
            stage('Build') {
                steps {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }
        post {
            failure {
                // Requirement: Notify and simulate failure
                echo "ALERT: ${fullJobName} failed!"
                mail to: 'madhusudhanachary.k@gmail.com',
                     subject: "Job Failed: ${fullJobName}",
                     body: "Build failed. View logs: ${env.BUILD_URL}"
                
                // Requirement: Archive failed logs
                sh 'echo "Error details" > failure_report.txt'
                archiveArtifacts artifacts: 'failure_report.txt'
            }
            always {
                // Requirement: Manage disk usage/Cleanup
                cleanWs()
            }
        }
    }
}
