pipeline {
agent {
    label 'master'
}

stages {
    stage('Build') {
        steps {
            echo "Hello"
        }
    }
    stage('Deploy') {
			when {
				env.JOB_NAME.endsWith('nightly')
			}
			steps {
				sh 'echo "Deploy on nighlty"'
			}
    }
}
