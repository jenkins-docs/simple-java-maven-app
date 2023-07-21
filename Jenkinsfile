node {
    stage('Build'){
        docker.image('maven:3.9.0').inside('-v /root/.m2:/root/.m2') {
            checkout scm
            sh 'mvn -B -DskipTests clean package'
        }
    }
    stage('Test'){
        docker.image('maven:3.9.0').inside('-v /root/.m2:/root/.m2') {
          sh 'mvn test'
          junit 'target/surefire-reports/*.xml'
        }    
    }      
}
