pipeline {
  agent any
  stages {
    stage('Build') {
      agent {
        docker {
          image 'maven:3.8.1-openjdk-11'
          args '-v $HOME/.m2:/root/.m2'
        }

      }
      steps {
        sh '''#rm -rf ~/.m2/repository/*

mvn clean compile'''
      }
    }

    stage('Test') {
      agent {
        docker {
          args '-v $HOME/.m2:/root/.m2'
          image 'maven:3.8.1-openjdk-11'
        }

      }
      steps {
        sh '''mvn test

#mvn -s settings.xml clean verify \\
#org.sonarsource.scanner.maven:sonar-maven-plugin:sonar \\
#  -Dsonar.projectKey=simple-java-maven-app \\
#  -Dsonar.projectName=\'simple-java-maven-app\' \\
#  -Dsonar.host.url=http://10.0.0.143:9000 \\
#  -Dsonar.token=sqp_e547a41aaeb1ccc94e15aad2f13f836879c43a60'''
      }
    }

    stage('Package') {
      agent {
        docker {
          image 'maven:3.8.1-openjdk-11'
          args '-v $HOME/.m2:/root/.m2'
        }

      }
      steps {
        sh '''mvn package -DskipTests -s settings.xml

'''
        stash(name: 'build-artifacts', includes: 'target/*.jar')
        archiveArtifacts(artifacts: 'target/*.jar', fingerprint: true)
      }
    }

    stage('Prepare Artifacts') {
      steps {
        unstash 'build-artifacts'
        script {
          def jarFileOutput = sh(
            script: 'ls target/*.jar 2>/dev/null | head -1 || echo ""',
            returnStdout: true
          ).trim()

          if (jarFileOutput) {
            env.JAR_FILE = jarFileOutput.split('/').last()
            echo "Found jar file: ${env.JAR_FILE}"
          } else {
            error "No jar file found, please check Package stage"
          }
        }

      }
    }

    stage('Build Docker Image') {
      steps {
        script {
          def fullImageName = "${ACR_REGISTRY}/${ACR_NAMESPACE}/${IMAGE_NAME}:${IMAGE_TAG}"
          env.FULL_IMAGE_NAME = fullImageName

          docker.build(fullImageName)
        }

      }
    }

    stage('Push to ACR') {
      steps {
        script {
          def fullImageName = env.FULL_IMAGE_NAME
          def latestImageName = "${ACR_REGISTRY}/${ACR_NAMESPACE}/${IMAGE_NAME}:latest"
          env.LATEST_IMAGE_NAME = latestImageName
        }

        withCredentials(bindings: [usernamePassword(
                                                                                                                                                                                                                                                            credentialsId: ACR_CREDENTIALS_ID,
                                                                                                                                                                                                                                                            usernameVariable: 'ACR_USERNAME',
                                                                                                                                                                                                                                                            passwordVariable: 'ACR_PASSWORD'
                                                                                                                                                                                                                                                        )]) {
            sh "docker login -u ${ACR_USERNAME} -p ${ACR_PASSWORD} ${ACR_REGISTRY}"
          }

          sh "docker push ${env.FULL_IMAGE_NAME}"
          sh "docker tag ${env.FULL_IMAGE_NAME} ${env.LATEST_IMAGE_NAME}"
          sh "docker push ${env.LATEST_IMAGE_NAME}"
        }
      }

    }
    environment {
      ACR_REGISTRY = 'registry.cn-guangzhou.aliyuncs.com'
      ACR_NAMESPACE = 'wayne-lee'
      IMAGE_NAME = 'testbuild'
      IMAGE_TAG = "${env.BUILD_ID}"
      ACR_CREDENTIALS_ID = 'aliyun-docker-creds'
    }
    post {
      success {
        echo 'Pipeline执行成功！镜像已推送到阿里云ACR'
        echo "镜像地址: ${env.FULL_IMAGE_NAME}"
      }

      failure {
        echo 'Pipeline failed, please check the logs'
      }

    }
  }