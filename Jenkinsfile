pipeline {
    agent any
        tools {
        //maven integration
            maven "MAVEN_HOME"
            }

    stages {
        stage('clone')
        {
            steps 
            {
                // Get some code from a GitHub repository
                git 'https://github.com/shabbeermca2010/simple-java-maven-app'
                branch 'master'
            }
        }
        stage('build')
        {    
            steps
            {
                // Run Maven on a Unix agent.
                //sh "mvn -Dmaven.test.failure.ignore=true clean package"

                // To run Maven on a Windows agent, use
                 bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }
            
        }
    }
        post
            {
            always
            {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                
                    junit '**/target/surefire-reports/TEST-*.xml'
                     archiveArtifacts 'target/*.jar'
                   
                }
            
            }
    
       
}
