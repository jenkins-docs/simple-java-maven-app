pipeline {
    // We set agent to 'none' at the top level so we can specify agents for each stage
    agent none 

    environment {
        APP_NAME = "Simple-Java-App"
    }

    stages {
        stage('Build and Test') {
            // Task: Assign build jobs to specific agent
            agent { label 'build-worker' } 
            steps {
                git branch: 'main', url: 'https://github.com/Madhu427/simple-java-maven-app.git'
                
                // Task: Simulate unit tests with retry logic
                retry(2) {
                    sh '''
                        echo "Running tests on BUILD-WORKER node..."
                        TEST_RESULT=0
                        if [ $TEST_RESULT -eq 0 ]; then
                            echo "Tests Passed!"
                        else
                            exit 1
                        fi
                    '''
                }
                
                // Task: Save and Archive build artifacts
                sh 'tar -czvf app-build-${BUILD_NUMBER}.tar.gz ./*'
                archiveArtifacts artifacts: '*.tar.gz', fingerprint: true
            }
        }

        stage('Deploy to Production') {
            // Task: Assign different jobs/stages to different agents
            agent { label 'production-slave' }
            // Task: Use 'when' directive to ensure deployment only happens on main
            when { branch 'main' } 
            steps {
                sh '''
                    echo "Deploying to PRODUCTION node..."
                    echo "Unpacking artifacts and simulating deployment..."
                    # In a real scenario, you would copy the artifact from the master/build node
                    echo "Deployment to Production Salve Successful!"
                '''
            }
        }
    }

    post {
        always {
            echo "Pipeline finished. Check console logs for 'Building remotely on...' to verify nodes."
        }
    }
}
