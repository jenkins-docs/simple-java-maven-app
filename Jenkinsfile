pipeline {

    agent any 
    stages {
        stage('Cleaning Stage') {
            steps {
                echo 'This will clean maven project by deleting the target directory.!!'
                sh "mvn clean"
            }
        }

        stage('Testing Stage') { 
            steps {
                echo 'This stage will run the test cases of the project.'
                sh "mvn test"
                
            }
        }

        stage('Packageing Stage') { 
            steps {
                echo 'This stage will build the maven project and packages them into a JAR/WAR'
                sh "mvn package"
    }
  }
 }
}