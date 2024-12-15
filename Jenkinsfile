// node {
//     try {
//         // Use the Maven installation specified in Jenkins
//         withMaven(maven: 'Maven 3.6.3') {
//             stage('Build') {
//                 // Skipping tests during package
//                 sh 'mvn -B -DskipTests clean package'
//             }

//             stage('Test') {
//                 // Run tests
//                 sh 'mvn test'
                
//                 // Always publish test results
//                 junit 'target/surefire-reports/*.xml'
//             }

//             stage('Deliver') {
//                 // Run delivery script
//                 sh './jenkins/scripts/deliver.sh'
//             }
//         }
//     } catch (Exception e) {
//         // Handle any exceptions
//         currentBuild.result = 'FAILURE'
//         throw e
//     }
// }

// Declarative Pipeline

pipeline {
    agent any
    tools { 
        maven '3.9.9' 
        jdk 'jdk-21' 
    }
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('Deliver') { 
            steps {
                sh './jenkins/scripts/deliver.sh' 
            }
        }
    }
}