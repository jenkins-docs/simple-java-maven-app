pipeline {
    agent any

    stages {

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
     stage(" Snyk ") {
       steps {
        sh "curl -sL https://raw.githubusercontent.com/snyk/snyk/master/tools/install.sh | bash"
        sh "snyk test --file pom.xml" // Use pom.xml for Maven projects
        failureThreshold 0 // Optional: Fail pipeline if vulnerabilities found
        }
       }

    }
}