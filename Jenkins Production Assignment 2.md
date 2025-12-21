                                                          Jenkins Production Assignment 2


<img width="523" height="199" alt="image" src="https://github.com/user-attachments/assets/710411e0-225a-4894-a6e7-e67e1ea37a57" />


##############################################################
pipeline {
    agent { label 'java-build-node' } // Scalability/Distributed requirement

    tools {
        maven 'M3' // Matches the name you set in Global Tool Configuration
    }

    options {
        retry(2) // Graceful failure management
        timeout(time: 15, unit: 'MINUTES')
    }

    environment {
        // Simulating a persistent cache location for Maven dependencies
        M2_CACHE = "/var/lib/jenkins/maven-cache" 
        PROD_CRED = credentials('prod-api-key') // Security requirement
    }

    stages {
        stage('Checkout') {
            steps {
                // Pulls code from your specific URL
                git 'https://github.com/Madhu427/simple-java-maven-app.git'
            }
        }

        stage('Build') {
            steps {
                echo "Building with Maven Caching..."
                // Use -Dmaven.repo.local to simulate logical caching of dependencies
                sh "mvn -Dmaven.repo.local=${M2_CACHE} clean compile"
            }
        }

        stage('Test') {
            steps {
                echo "Running Unit Tests..."
                sh "mvn -Dmaven.repo.local=${M2_CACHE} test"
            }
        }

        stage('Package') {
            steps {
                sh "mvn -Dmaven.repo.local=${M2_CACHE} package -DskipTests"
                // Archive the resulting JAR file
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            }
        }

        stage('Deploy (Simulated)') {
            steps {
                echo "Deploying JAR to production using ${PROD_CRED}..."
                // Logic check: ensure the JAR exists before "deploying"
                sh '[ -f target/*.jar ] && echo "Deployment Success" || exit 1'
            }
        }
    }

    post {
        failure {
            echo "Build failed. Alerting DevOps Team..."
        }
    }
}
#########################################################################

<img width="530" height="378" alt="image" src="https://github.com/user-attachments/assets/95ce7a46-115c-4a72-a833-c990d2947fa8" />



