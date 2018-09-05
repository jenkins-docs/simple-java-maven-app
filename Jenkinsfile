node {
    def app
    
    stage('Clone repository') {
        checkout scm
    }

    stage('Maven clean install settings') {
        sh 'mvn clean install -s settings.xml'
    }

    stage('Build image') {
        app = docker.build("image-of-justice")
    }

    stage('Maven build') {
        sh 'mvn -B -DskipTests clean package' 
    }

    stage('Test image') {
        app.inside {
            sh 'echo "Tests passed"'
        }
    }

    stage('Test Maven build') {
        sh 'mvn test'
        junit 'target/surefire-reports/*.xml'
    }

    stage('Deploy Maven build') {
        sh 'mvn deploy'
    }
}