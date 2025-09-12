pipeline{
    agent any
    tools{
        maven "MAVEN"
    }
    stages{
        stage("build"){
            steps{
                bat "mvn clean package"
            }
        }
        stage("test"){
            parallel{
                steps{
                    echo "test 1"
                }
                steps{
                    "test 2"
                }
            }
        }
        stage("run"){
            steps{
                bat "java -cp target/my-app-1.0-SNAPSHOT.jar com.mycompany.app.App"
            }
        }
        
    }
    post {
        success{
            archiveArtifacts artifacts: "**/target/*.jar"
        }
    }
}
