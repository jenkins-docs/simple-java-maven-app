pipeline {
    agent { label 'production-slave' }

    stages {
        stage('Checkout Source') {
            steps {
                // Task: Source Code Management with Git
                // For Job A use 'dev', for Job B use 'feature'
                git branch: 'feature', url: 'https://github.com/Madhu427/simple-java-maven-app.git'
            }
        }

        stage('Build and Simulate Tests') {
            steps {
                // Task: Run basic shell script logic to simulate unit tests
                sh '''
                    #!/bin/bash
                    echo "Starting Build and Test Process..."
                    
                    # Simulate Test Result
                    TEST_RESULT=0 
                    
                    if [ $TEST_RESULT -eq 0 ]; then
                        echo "Unit Tests Passed Successfully!"
                    else
                        echo "Unit Tests Failed!"
                        exit 1
                    fi

                    # Task: Save build artifacts (fake .tar.gz files) in workspace
                    echo "Generating Build Version: 1.0.$BUILD_NUMBER" > build_info.txt
                    tar -czvf service-build-${BUILD_NUMBER}.tar.gz build_info.txt
                '''
            }
        }
    }

    post {
        failure {
            // Task: Send mock echo alert to console
            echo "************************************************"
            echo "ALERT: Build Failed on Stage: ${env.STAGE_NAME}"
            echo "Sending notification to: madhusudhanachary.k@gmail.com"
            echo "************************************************"

            // Task: Archive failed logs in a dedicated location
            sh 'echo "Error details for Build #${BUILD_NUMBER}" > failure_report.txt'
            sh 'date >> failure_report.txt'
            
            archiveArtifacts artifacts: 'failure_report.txt', fingerprint: true
        }
       always {
            // Task: Implement a cleanup step to remove old artifacts
            cleanWs() 
            echo "Workspace cleaned after build execution."
        }
    }
}
