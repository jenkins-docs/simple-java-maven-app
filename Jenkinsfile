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
           when (env.JOB_NAME.endsWith('nightly')
                sh 'echo "Deploy on nighlty"'
            when (env.JOB_NAME.endsWith('sprintly')
                sh 'echo "Deploy only sprintly"'
    }
}
