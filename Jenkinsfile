node {
    def mavenTool = tool name: '3.9.9', type: 'maven'
    def jdkTool = tool name: 'jdk-21', type: 'jdk'
    def dockerImage = 'liloid/java-maven-app:latest'
    def ec2Host = '13.229.208.132'
    def ec2User = 'ec2-user'

    try {
        stage('Debug Tests') {
            withEnv(["JAVA_HOME=${jdkTool}", "PATH+MAVEN=${mavenTool}/bin"]) { 
                sh 'pwd'
            }
            checkout([$class: 'GitSCM', branches: [[name: '*/dev']], 
                userRemoteConfigs: [[url: 'file:///home/Documents/devops/cicd/simple-java-maven-app']]])
            }
        }

        stage('Build') {
            withEnv(["JAVA_HOME=${jdkTool}", "PATH+MAVEN=${mavenTool}/bin"]) { 
                sh 'mvn clean package'
                sh 'ls -la'
                sh "docker build -t ${dockerImage} ."
            }
        }

        stage('Test') {
            withEnv(["JAVA_HOME=${jdkTool}", "PATH+MAVEN=${mavenTool}/bin"]) {
                try {
                    sh 'mvn test'
                } finally {
                    sh 'ls -la target/surefire-reports/'
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Manual Approval') {
            withEnv(["JAVA_HOME=${jdkTool}", "PATH+MAVEN=${mavenTool}/bin"]) {
                sh './jenkins/scripts/deliver.sh' 
                input message: 'Lanjutkan ke tahap Deploy?'
            }
        }

        stage('Deploy') {
            withEnv(["JAVA_HOME=${jdkTool}", "PATH+MAVEN=${mavenTool}/bin"]) {
                withDockerRegistry([credentialsId: 'docker-hub-credentials', url: '']) {
                    sh "docker push ${dockerImage}"
                }

                // sh """
                // ssh -i ${sshKey} ${ec2User}@${ec2Host} << EOF
                // docker pull ${dockerImage}
                // docker stop java-maven-app || true
                // docker rm java-maven-app || true
                // docker run -d --name java-maven-app -p 8080:8080 ${dockerImage}
                // EOF
                // """

                sh './jenkins/scripts/deliver.sh' 
                echo 'Aplikasi akan berjalan selama 1 menit...'
                sleep(time: 1, unit: 'MINUTES')
                echo 'Waktu habis, aplikasi akan dihentikan.'
            }
        }
    } catch (e) {
        echo "Build failed in stage '${currentBuild.currentResult}': ${e.getMessage()}"
        currentBuild.result = 'UNSTABLE' 
    } finally {
        if (currentBuild.result == 'UNSTABLE') {
            echo "Pipeline marked as unstable." 
        }
    }
}
