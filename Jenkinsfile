pipeline {
    agent { label 'production-slave' }

    stages {
        stage('Checkout Source') {
            steps {
                // Task: Source Code Management with Git
                // For Job A use 'dev', for Job B use 'feature'
                git branch: 'dev', url: 'https://github.com/Madhu427/simple-java-maven-app.git'
            }
        }

        stage('Build and Simulate Tests') {
            steps {
                // Task: Run basic shell script logic to simulate unit tests
                sh '''
                    #!/bin/bash
                    echo "Starting Build and Test Process..."
                    
                    # Simulate Test Result
                    TEST_RESULT=1 
                    
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
        
            // Task 5: Send a real email notification
            mail to: 'madhusudhanachary.k@gmail.com',
                 subject: "Failed Pipeline: ${currentBuild.fullDisplayName}",
                 body: "Something went wrong with build ${env.BUILD_URL}. Check the archived logs."

            // Task 5: Archive failed logs in a dedicated location
            sh 'mkdir -p failure_logs'
            sh 'echo "Failure detected on node: ${NODE_NAME}" > failure_logs/build_error.txt'
            archiveArtifacts artifacts: 'failure_logs/*.txt', fingerprint: true
        }
       always {
            // Task: Implement a cleanup step to remove old artifacts
            cleanWs() 
            echo "Workspace cleaned after build execution."
        }
    }
}
