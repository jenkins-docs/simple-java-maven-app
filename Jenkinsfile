pipeline {
    agent any
    
    stages {
        stage('Install Snyk') {
            steps {
                sh "curl -sL https://raw.githubusercontent.com/snyk/snyk/master/tools/install.sh | bash"
            }
        }
        stage('Build') { 
            steps {
                sh 'mvn -B -DskipTests clean package' 
            }
        }
        stage("Test") {
    steps {
        echo "Testing the app"
        // Make sure Maven is installed
        sh "mvn -v"

        // Run unit tests
        sh "mvn test"

        // Run integration tests
        sh "mvn integration-test"

        // Run acceptance tests
        sh "mvn verify"

    }
}
stage("Snyk Scan") {
            steps {
                sh "snyk monitor --file package.json"  // Adjust command based on your project
            }
        }

    }
}