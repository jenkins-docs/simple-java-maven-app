node {
  stage('Build') {
    docker.image('maven:3-alpine').inside('-v /root/.m2:/root/.m2') {
      sh 'mvn -B -DskipTests clean package'
    }
  }
}
