pipeline {
    agent {
        docker {
            image 'maven:3-alpine' 
            args '-v /root/.m2:/root/.m2' 
        }
    }
    stages {
        stage('Build') { 
            steps {
                sh 'echo yujin' 
                sh 'mvn -B -gs maven-setting.xml -DskipTests clean package' 
                mail to: 'yujin19861013@163.com',
                     subject: "Failed Pipeline: ${env.BRANCH_NAME}",
                     body: "Something is DALIAN GOOD wrong with ${env.WORKSPACE}"
            }
        }
    }
}
