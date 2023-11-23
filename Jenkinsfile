pipeline {
  agent {
    docker {
      image "maven:3.9.5-eclipse-temurin-17-alpine" /* (1) */
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
