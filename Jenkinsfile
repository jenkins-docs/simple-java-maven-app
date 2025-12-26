pipeline {
    agent any

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
        always {
            // Task: Archive build artifacts
            // This ensures *.tar.gz files are saved and visible on the job page
            archiveArtifacts artifacts: '*.tar.gz', fingerprint: true
        }
    }
}
