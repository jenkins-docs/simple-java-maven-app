// node {
//     def mavenTool = tool name: '3.9.9', type: 'maven'
//     def jdkTool = tool name: 'jdk-21', type: 'jdk'

//     try {
//         stage('Debug Tests') {
//             withEnv(["JAVA_HOME=${jdkTool}", "PATH+MAVEN=${mavenTool}/bin"]) { 
//                 sh 'ls -R src/test/java/' 
//             }
//         }

//         stage('Build') {
//             withEnv(["JAVA_HOME=${jdkTool}", "PATH+MAVEN=${mavenTool}/bin"]) { 
//                 sh 'mvn clean package' 
//             }
//         }

//         stage('Test') {
//             withEnv(["JAVA_HOME=${jdkTool}", "PATH+MAVEN=${mavenTool}/bin"]) {
//                 try {
//                     sh 'mvn test'
//                 } finally {
//                     sh 'ls -la target/surefire-reports/'
//                     junit 'target/surefire-reports/*.xml'
//                 }
//             }
//         }

//         stage('Deploy') {
//             withEnv(["JAVA_HOME=${jdkTool}", "PATH+MAVEN=${mavenTool}/bin"]) {
//                 sh './jenkins/scripts/deliver.sh' 
//                 input message: 'Sudah selesai menggunakan Java App? (Klik "Proceed" untuk mengakhiri)'
//             }
//         }
//     } catch (e) {
//         echo "Build failed in stage '${currentBuild.currentResult}': ${e.getMessage()}"
//         currentBuild.result = 'UNSTABLE' 
//     } finally {
//         if (currentBuild.result == 'UNSTABLE') {
//             echo "Pipeline marked as unstable." 
//         }
//     }
// }
pipeline {
    agent any
    stages {
        stage('Clean Workspace') {
            steps {
                deleteDir()
            }
        }
        stage('Checkout') {
            steps {
                checkout scm
                sh 'ls -R'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
                sh 'ls -R src/test/java/'
            }
        }
    }
}
