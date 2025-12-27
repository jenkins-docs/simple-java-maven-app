def call(Map config) {
    // 1. Apply Naming Conventions: Automatically prefix job names
    def standardizedName = "PROD-${config.appName}-${env.BUILD_NUMBER}"
    
    pipeline {
        agent none
        stages {
            stage('Standard Checkout') {
                agent { label 'build-worker' }
                steps {
                    // Reusable Checkout
                    git url: config.repoUrl, branch: 'main'
                    echo "Checking out ${standardizedName}"
                }
            }
            stage('Standard Build') {
                agent { label 'build-worker' }
                steps {
                    // Reusable Build logic
                    sh 'mvn clean package -DskipTests'
                }
            }
        }
        post {
            failure {
                // Reusable Notify
                mail to: 'admin@company.com',
                     subject: "FAILED: ${standardizedName}",
                     body: "Build failed on node ${NODE_NAME}. Check logs at ${env.BUILD_URL}"
            }
        }
    }
}
