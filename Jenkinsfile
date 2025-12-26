pipeline {
    agent any
    
    // Task 3: Use parameterized jobs for dynamic selection
    parameters {
        string(name: 'BRANCH_NAME', defaultValue: 'master', description: 'Branch selection (e.g., master or feature)')
        booleanParam(name: 'DEBUG_MODE', defaultValue: false, description: 'Debug mode toggle')
        choice(name: 'ENVIRONMENT', choices: ['dev', 'staging', 'prod'], description: 'Simulated environment selection')
    }

    stages {
        stage('Checkout Source') {
            steps {
                // Task: Source Code Management with Git
                // Uses the BRANCH_NAME parameter for dynamic cloning
                git branch: "${params.BRANCH_NAME}", url: 'https://github.com/Madhu427/simple-java-maven-app.git'
            }
        }

        stage('Build and Simulate Tests') {
            steps {
                // Task: Run basic shell script logic to simulate unit tests
                sh '''
                    #!/bin/bash
                    echo "Starting Build on Environment: ${ENVIRONMENT}"
                    
                    # Check Debug Mode toggle
                    if [ "${DEBUG_MODE}" = "true" ]; then
                        echo "DEBUG: System path is $PATH"
                        set -x # Enable verbose logging
                    fi

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
                    echo "Environment: ${ENVIRONMENT}" >> build_info.txt
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
