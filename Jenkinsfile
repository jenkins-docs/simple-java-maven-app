pipeline {

agent any
stages{
    stage("Checkout"){
        steps{
            echo "Downloading Source Code"
            git 'https://github.com/prashhantss/simple-java-maven-app.git'
            sh "ls -l"
            sh "pwd"
        }
    }
}

}
