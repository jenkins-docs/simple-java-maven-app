pipeline {
agent {
docker {
image 'maven:3.8.1-adoptopenjdk-11'
args '-v /root/.m2:/root/.m2'
}
}
stages {
stage('Build') {
steps {
sh 'mvn -B -DskipTests clean package'
}
}
stage('Test') { //1
steps {
sh 'mvn test' //2
}
post {
always {
junit 'target/surefire-reports/*.xml' //3
}
}
}
}
}