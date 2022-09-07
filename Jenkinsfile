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
//      sh "/opt/apache-maven-3.6.3/bin/mvn clean package"
      }
   stage('SonarQube Analysis') {
      //code analysis
      def mvnHome = tool name: 'mvn', type: 'maven'
      def mvn = "${mvnHome}/bin/mvn"
      withSonarQubeEnv(credentialsId: 'sonar', installationName: 'sonarqube') {
        sh "${mvn} verify sonar:sonar -Dsonar.projectKey=sample-springhello"
        }
      }
   stage("Quality Gate Check"){
      timeout(time: 1, unit: 'HOURS') { // Just in case something goes wrong, pipeline will be killed after a timeout
          def qg = waitForQualityGate() // Reuse taskId previously collected by withSonarQubeEnv
          if (qg.status != 'OK') {
              error "Pipeline aborted due to quality gate failure: ${qg.status}"
           }
        }
      }
}
