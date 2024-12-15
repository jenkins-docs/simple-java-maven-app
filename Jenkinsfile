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

// pipeline {
//     agent any
//     tools { 
//         maven '3.9.9' 
//         jdk 'jdk-21' 
//     }
//     options {
//         skipStagesAfterUnstable()
//     }
//     stages {
//         stage('Build') {
//             steps {
//                 sh 'mvn -B -DskipTests clean package'
//             }
//         }
//         stage('Test') {
//             steps {
//                 sh 'mvn test'
//             }
//             post {
//                 always {
//                     junit 'target/surefire-reports/*.xml'
//                 }
//             }
//         }
//         stage('Deliver') { 
//             steps {
//                 sh './jenkins/scripts/deliver.sh' 
//             }
//         }
//     }
// }

// Scripted Pipeline
node {
    def mavenTool = tool name: '3.9.9', type: 'maven'
    def jdkTool = tool name: 'jdk-21', type: 'jdk'
    env.PATH = "${jdkTool}/bin:${mavenTool}/bin:${env.PATH}"

    def skipStages = false

    try {
        stage('Build') {
            if (skipStages) {
                echo 'Skipping Build stage because a previous stage was unstable.'
            } else {
                sh "${mavenTool}/bin/mvn -B -DskipTests clean package"
            }
        }

        stage('Test') {
            if (skipStages) {
                echo 'Skipping Test stage because a previous stage was unstable.'
            } else {
                try {
                    sh "${mavenTool}/bin/mvn test"
                } finally {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Deliver') {
            if (skipStages) {
                echo 'Skipping Deliver stage because a previous stage was unstable.'
            } else {
                sh './jenkins/scripts/deliver.sh'
            }
        }
    } catch (e) {
        skipStages = true
        echo "Build failed: ${e}"
        currentBuild.result = 'UNSTABLE'
    } finally {
        if (skipStages) {
            echo "Pipeline marked as unstable. Skipped remaining stages."
        }
    }
}
