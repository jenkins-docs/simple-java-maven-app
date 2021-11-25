
node('master') {
    

    
    stage('checkout') {
        git changelog: false, credentialsId: 'c0e88c53-d623-467d-9f85-3e5f9f28c305', poll: false, url: 'https://github.com/vandana-gaur/simple-java-maven-app.git'
    }
    stage('Compile') {
        bat 'mvn compile'
    }

     stage('Build') {
        bat 'mvn package'
    }

    
    stage('Send mail') {
        mail bcc: '', body: 'Hi!!', cc: '', from: '', replyTo: '', subject: 'Compile completed', to: 'vandanatechacc@gmail.com'
    }
}

 
