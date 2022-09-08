node {
  def maven = docker.image('maven:3.5.4-alpine')
  stage('Build') {
    maven.inside('-v /root/.m2:/root/.m2') {
      sh 'mvn -B -DskipTests clean package'
    }
  }
  stage('Test') {
    maven.inside('-v /root/.m2:/root/.m2') {
      sh 'mvn test'
    }
  }
}
