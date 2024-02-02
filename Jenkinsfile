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
     stage(" Delivering the app ") {
        when {
                branch 'develop'
            }
            steps {
                sh 'develop.sh'
            }
       }

       stage(" Deploy for production ") {
        when {
                branch 'prod'
            }
            steps {
                sh 'deployment.sh'
            }
       }

    }
}