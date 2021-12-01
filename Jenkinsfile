pipeline {
agent {
docker {
image 'maven:3.8.1-adoptopenjdk-11' //1
args '-v /root/.m2:/root/.m2' //2
}
}
stages {
stage('Build') { //3
steps {
sh 'mvn -B -DskipTests clean package' //4
}
}
}
}
