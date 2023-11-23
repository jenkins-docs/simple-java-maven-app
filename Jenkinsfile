pipeline {
  agent {
    docker {
      image "arm64v8/maven:3-eclipse-temurin-21-alpine"
      args '-v /root/.m2:/root/.m2' /* (2) */
    }
  }

  stages {
    stage("Build") {
      steps {
        sh "mvn -B -DskipTests clean package" /* (3) */
      }
    }
  }
}
