pipeline {
    agent any

    environment {
        // Task: Use of environment variables
        APP_NAME = "MultiService-App"
        CACHE_DIR = "maven_cache"
    }

    stages {
        stage('Initialize & Cache') {
            steps {
                // Task: Simulate caching
                sh '''
                    if [ -d "${CACHE_DIR}" ]; then
                        echo "Dependencies found in cache. Skipping download."
                    else
                        echo "Cache empty. Downloading dependencies..."
                        mkdir ${CACHE_DIR}
                        echo "dependency-v1.0" > ${CACHE_DIR}/lib.txt
                    fi
                '''
            }
        }

        stage('Checkout Source') {
            steps {
                // Task: Proper use of stages and steps
                git branch: 'main', url: 'https://github.com/Madhu427/simple-java-maven-app.git'
            }
        }

        stage('Simulate Tests') {
            // Task: Implement retry logic for test stages (max 2 retries)
            
                steps {
                    retry(2) {
                    sh '''
                        echo "Running Unit Tests..."
                        # Simulate a 10% chance of failure to test retry logic
                        if [ $(( $RANDOM % 10 )) -eq 0 ]; then
                            echo "Random network failure detected!"
                            exit 1
                        fi
                        echo "Tests Passed!"
                    '''
                    }
                 }
              }

        stage('Build & Package') {
            // Task: Use of when directive to conditionally run stages
            // Only runs if we are on the 'main' or 'prod' branch
            when {
                anyOf {
                    branch 'main'; branch 'prod'
                }
            }
            steps {
                sh '''
                    echo "Packaging ${APP_NAME}..."
                    tar -czvf service-build-${BUILD_NUMBER}.tar.gz ./*
                '''
            }
        }
    }

    post {
        // Task: Proper use of post conditions
        success {
            echo "Build and Test successful! Ready for Deployment."
            archiveArtifacts artifacts: '*.tar.gz', fingerprint: true
        }
        failure {
            echo "Build failed. Check the logs for caching or test errors."
        }
    }
}
