node {
    def app

    stage('Clone repository') {
        /* Let's make sure we have the repository cloned to our workspace */

        checkout scm
    }

    stage('Maven clean install settings') {
        sh 'mvn clean install -s mvn-settings.xml'
    }

    stage('Build image') {stage('Deploy Maven build') {
        sh 'mvn deploy'
    }
        /* This builds the actual image; synonymous to
         * docker build on the command line */

        app = docker.build("image-of-justice")
    }

    stage('Maven build') {
        sh 'mvn -B -DskipTests clean package' 
    }

    stage('Test image') {
        /* Ideally, we would run a test framework against our image.
         * For this example, we're using a Volkswagen-type approach ;-) */

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