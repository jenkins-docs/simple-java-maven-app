pipeline {
    agent any

	tools {

    maven 'maven-3.8'

	}
    stages {
        stage("test") {
            steps {
                script {
                   echo "testing the application for $BRANCH_NAME"
                }
            }
        }
        stage("build image") {

           when {
            expression {

                BRANCH_NAME == 'master'
            }

           }


            steps {
                script {
                    echo "building docker image "
					withCredentials([usernamePassword(credentialsId: 'docker-hub-pass', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {

                        sh 'docker build -t omarkhaledmah/omar-repo:java-maven-app546.0  .'
                        sh "echo $PASSWORD | docker login -u $USERNAME --password-stdin"
                        sh 'docker push omarkhaledmah/omar-repo:java-maven-app545.0'
                }


                }
            }
        }
        
        stage("deploy") {
            when {
            expression {

                BRANCH_NAME == 'master'
            }

           }
            steps {
                script {
                    echo "deploying"
     
                }
            }
        }
    }   
}
