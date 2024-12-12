node {
    try {
        // Equivalent to agent any
        
        stage('Build') {
            // Skipping tests during package
            sh 'mvn -B -DskipTests clean package'
        }
        
        stage('Test') {
            // Run tests
            sh 'mvn test'
            
            // Always publish test results
            junit 'target/surefire-reports/*.xml'
        }
        
        stage('Deliver') {
            // Run delivery script
            sh './jenkins/scripts/deliver.sh'
        }
    } catch (Exception e) {
        // Handle any exceptions
        currentBuild.result = 'FAILURE'
        throw e
    }
}