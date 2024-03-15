pipeline {
    agent any
    stages {
        stage("Build") {
            steps {
                sh 'mvn install'
            }
        }
        stage("Test") {
            steps {
                sh "mvn test"
            }
        }
        stage("CompiledandRunSonarAnalysis") {
            steps{
                sh 'mvn clean verify sonar:sonar -Dsonar.projectkey=hackdossier -Dsonar.organization=hackdossier -Dsonar.host.url=https://sonarcloud.io/ -Dsonar.token=ec9cdb38d5e716f14385f39185bd601de93c0ae1'
            }
        }
        stage("SnykSCAScan") {
            steps {
                withCredentials(strings[credentails:    "SNYK_TOKEN", variable:SNYK_TOKEN]){
                    sh "mvn snyk:test -fn"
                }
            }
        }
        stage("Build")  {
            steps{
                withDockerRegistry([withCredentialsId: "dockerlogin", url:""])  {
                    script{
                        app = docker.build("hd-image-storage-1")
                    }
                }
            }
        }
        stage(push) {
            steps{
                docker.withRegistry('https://058264359241.dkr.ecr.us-east-1.amazonaws.com/','ecr:us-east-1:aws-credentials')  {
                    app.push("latest")
                }
            }
        }
    }
}

