pipeline {

    agent any

    tools {
        maven 'Maven3'
    }

    environment {

        MAVEN_REPO_ID = "artifactory-snapshot"
        MAVEN_REPO_URL = "http://artifactory:8081/artifactory/libs-snapshot-local"

        DOCKER_REGISTRY = "localhost:5000"
        IMAGE_NAME = "my-app"
        IMAGE_TAG = "${BUILD_NUMBER}"
    }

    stages {
        stage('Clean Workspace') {
            steps {
                deleteDir()
            }
        }

        stage('Checkout') {
            steps {
                git 'https://github.com/danyredjim/simple-java-maven-app.git'
            }
        }

        stage('Configure Maven Settings') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'artifactory-creds',
                    usernameVariable: 'ART_USER',
                    passwordVariable: 'ART_PASS'
                )]) {

                    writeFile file: 'settings.xml', text: """
                    <settings>
                      <servers>
                        <server>
                          <id>${MAVEN_REPO_ID}</id>
                          <username>${ART_USER}</username>
                          <password>${ART_PASS}</password>
                        </server>
                      </servers>
                    </settings>
                    """
                }
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Unit Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

      //  stage("Quality Gate") {
      //      steps {
      //          timeout(time: 2, unit: 'MINUTES') {
      //              waitForQualityGate abortPipeline: true
      //          }
      //      }
      //  }

        stage('Deploy JAR to Artifactory') {
            steps {
                sh """
                mvn deploy \
                -DaltDeploymentRepository=${MAVEN_REPO_ID}::default::${MAVEN_REPO_URL} \
                --settings settings.xml
                """
            }
        }

        stage('Build Docker Image') {
            steps {
                sh """
                docker build -t ${DOCKER_REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG} .
                """
            }
        }

        stage('Push Docker Image') {
            steps {
                sh """
                docker push ${DOCKER_REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}
                """
            }
        }

    }

    post {

        success {
            echo "Pipeline completado correctamente 🚀"
        }

        failure {
            echo "Pipeline falló ❌"
        }

        always {
            junit '**/target/surefire-reports/*.xml'
        }
    }
}