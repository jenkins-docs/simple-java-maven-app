pipeline {
    agent any
	 parameters {
        string(name: 'Greeting', defaultValue: 'Hello', description: 'How should I greet the world?')
    }
    stages {
        stage('Example') {
            steps {
                echo 'Intiating ... CI/CD'
				
				echo "${params.Greeting} World!"

                script {
                    def browsers = ['chrome', 'firefox']
                    for (int i = 0; i < browsers.size(); ++i) {
                        echo "Testing the ${browsers[i]} browser"
                    }
                }
            }
        }
		stage('Build') {
            steps {
                'mvn -B -DskipTests clean package'
            }
        }
        stage('Test') {
            steps {
                'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
    }
}