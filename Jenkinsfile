pipeline {

    agent any 
    stages {
        stage('Cleaning Stage') {
            steps {
                echo 'This stage will clean maven project by deleting the target directory.!!'
                sh "mvn clean"
            }
        }

        stage('Testing Stage') { 
            steps {
                echo 'This stage will run the test cases of the project..!!'
                sh "mvn test"
                
            }
        }

        stage('Packageing Stage') { 
            steps {
                echo 'This stage will build the maven project and packages them into a JAR/WAR'
                sh "mvn package"
    }
  }
        stage("Results") {
            steps {
                input("Do you want to capture results?")
                junit '**/target/surefire-reports/TEST-*.xml'
                archive 'target/*.jar'
           }
        }
        stage("Email Build Status"){
            steps{
                mail bcc: '', body: 'Check Build Status', cc: '', from: '', replyTo: '', subject: 'Build Status', to: 'vikas.singh1312@outlook.com'
            }
        }
    }
}