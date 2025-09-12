pipeline {
    agent none  // assign agents per stage
    tools {
        maven "MAVEN"  // name of Maven installation in Jenkins
    }

    stages {
        stage("Build on Master") {
            agent { label 'built-in' }  // Windows master
            steps {
                bat "mvn clean package"
                // Archive the JAR so it can be copied to slaves
                archiveArtifacts artifacts: "target/*.jar", fingerprint: true
            }
        }

        stage("Run on Slaves") {
            parallel {
                stage("Run on Server1") {
                    agent { label 'server1' }  // Linux slave1
                    steps {
                        // Copy the artifact from the master
                        copyArtifacts(projectName: "${env.JOB_NAME}", selector: lastSuccessful())
                        sh "java -cp target/my-app-1.0-SNAPSHOT.jar com.mycompany.app.App"
                    }
                }
                stage("Run on Server2") {
                    agent { label 'server2' }  // Linux slave2
                    steps {
                        // Copy the artifact from the master
                        copyArtifacts(projectName: "${env.JOB_NAME}", selector: lastSuccessful())
                        sh "java -cp target/my-app-1.0-SNAPSHOT.jar com.mycompany.app.App"
                    }
                }
            }
        }
    }

    post {
        success {
            echo "Pipeline completed successfully!"
        }
    }
}
