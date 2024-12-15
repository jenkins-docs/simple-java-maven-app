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
        // stage('Deliver') { 
        //     steps {
        //         sh './jenkins/scripts/deliver.sh' 
        //     }
//         }
//     }
// }

// Scripted Pipeline
node {
    // Define tools
    def mavenTool = tool name: '3.9.9', type: 'maven'
    def jdkTool = tool name: 'jdk-21', type: 'jdk'
    
    properties([
        pipelineTriggers([cron('H/2 * * * *')])
    ])

    def skipStages = false

    try {
        stage('Build') {
            withEnv(["JAVA_HOME=${jdkTool}", "PATH+MAVEN=${mavenTool}/bin"]) { 
                sh 'mvn clean package' 
            }
        }

        stage('Test') {
            withEnv(["JAVA_HOME=${jdkTool}", "PATH+MAVEN=${mavenTool}/bin"]) {
                try {
                    sh 'mvn test'
                } finally {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Deliver') {
            withEnv(["JAVA_HOME=${jdkTool}", "PATH+MAVEN=${mavenTool}/bin"]) {
                sh './jenkins/scripts/deliver.sh' 
            }
        }
    } catch (e) {
        echo "Build failed in stage '${currentBuild.currentResult}': ${e.getMessage()}"
        currentBuild.result = 'UNSTABLE' 
    } finally {
        if (currentBuild.result == 'UNSTABLE') {
            echo "Pipeline marked as unstable." 
        }
    }
}

