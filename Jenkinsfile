pipeline {
   agent any
   tool{
       jdk: "jdk1.8"
       maven: "maven3.8"
   }
   stages {
        stage('Build') { 
            steps {
                sh 'mvn -B -DskipTests clean package' 
            }
        }
    }
}
