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
                mail body: "${env.JOB-NAME} - BUILD # ${env.BUILD_NUMBER}", to:'vikas.singh1312@outlook.com' 
            }
        }
    }
}