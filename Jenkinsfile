pipeline{
    agent any
    stages{
        stage("build"){
            steps{
                bat "mvn clean package"
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
