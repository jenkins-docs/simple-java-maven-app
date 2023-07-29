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
    stage('Manual Approval'){
        input message: 'Lanjutkan ke tahap deploy? (Click "Proceed" to continue)'      
    }
    stage('Deploy'){
        docker.image('maven:3.9.0').inside('-v /root/.m2:/root/.m2'){
           sh './jenkins/scripts/deliver.sh'
           sleep(60)
        }
    }
}

