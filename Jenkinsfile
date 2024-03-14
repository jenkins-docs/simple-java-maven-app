pipeline {
    agent {
        label 'windows' // Use a Windows agent
    }

    environment {
        MAVEN_HOME = 'C:\\Program Files\\Apache\\maven'  // Example path to Maven installation directory
        PATH = "$PATH;$MAVEN_HOME\\bin"
    }

    stages {
        stage('Install_Maven') {
            steps {
                script {
                    // Download and Install Maven
                    bat '''
                    curl -O https://downloads.apache.org/maven/maven-3/3.8.4/binaries/apache-maven-3.8.4-bin.zip
                    7z x apache-maven-3.8.4-bin.zip
                    mv apache-maven-3.8.4 'C:\\Program Files\\Apache\\maven'
                    '''
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    // Run Maven Test
                    bat '''
                    cd 'C:\\path\\to\\your\\project'
                    mvn test
                    '''
                }
            }
        }
    }
}
