pipeline {
    agent any

	tools {

    maven 'maven-3.8'

	}
    stages {
        stage("build jar file") {
            steps {
                script {
                    echo "building the application"
		    sh 'mvn package'
                }
            }
        }
        stage("build image") {
            steps {
                script {
                    echo "building docker image "
<<<<<<< HEAD
					withCredentials([usernamePassword(credentialsId: 'docker-hub-pass', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
=======
			withCredentials([usernamePassword(credentialsId: 'docker-hub-pass', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
>>>>>>> e6420fbe6fd7e2dcc33204a6e70af4097ef245f5

                        sh 'docker build -t omarkhaledmah/omar-repo:java-maven-app545.0  .'
                        sh "echo $PASSWORD | docker login -u $USERNAME --password-stdin"
                        sh 'docker push markhaledmah/omar-repo:java-maven-app545.0'
                }


                }
            }
        }
        
        stage("deploy") {
            steps {
                script {
                    echo "deploying"
     
                }
            }
        }
    }   
}
