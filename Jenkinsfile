node {
   stage('Code Checkout') {
      //Checkout the code from a GitHub repository
      git credentialsId: 'github_cred', url: 'https://github.com/akpatnaikuni/simple-java-maven-app.git'
      }
   stage('build') {
      //build the code
      def mvnHome = tool name: 'mvn', type: 'maven'
      def mvn = "${mvnHome}/bin/mvn"
      sh "${mvn} clean package"
#      sh "/opt/apache-maven-3.6.3/bin/mvn clean package"
      }
   stage('SonarQube Analysis') {
      //code analysis
      def mvnHome = tool name: 'mvn', type: 'maven'
      def mvn = "${mvnHome}/bin/mvn"
      withSonarQubeEnv() {
        sh "${mvn} clean verify sonar:sonar -Dsonar.projectKey=sample-springhello"
        }
      }
}
