node {
    stage('Build') { 
        mvn('clean install')
        authVerify()
    }
    stage('Tester') {
        mvn('test')
        junit 'target/surefire-reports/*.xml'
    }
    stage('SonarQube analysis') {
        sonar()
    }
    stage('QA') {
        timeout(time: 10, unit: 'MINUTES') {
            waitForQualityGate abortPipeline: true
        }
    }
    stage('Deliver') {
        def mvn_version = 'M3'
        withEnv( ["PATH+MAVEN=${tool mvn_version}/bin"] ) {
            sh './jenkins/scripts/deliver.sh'
        }
        artifactory()
    }
}

def mvn(args) {
    def mvn_version = 'M3'
    withEnv( ["PATH+MAVEN=${tool mvn_version}/bin"] ) {
        sh "mvn $args" 
    }
}
def sonar() {
    scanner = tool 'Scanner' 
    withSonarQubeEnv('Sonarqube') {
        sh "${scanner}/bin/sonar-scanner"
    }
}
def authVerify() {
    withCredentials([usernameColonPassword(credentialsId: 'fruity', variable: 'USERPASS')]) {
        def method = load("auth.groovy")
        method.auth(USERPASS)
    }
}
def artifactory() {
    withCredentials([usernamePassword(credentialsId: 'art', usernameVariable: 'USR', passwordVariable: 'PASS')]) {
        rtServer (
            id: "Artifactory-1",
            url: "http://172.17.0.3:8081/artifactory",
            username: "${USR}",
            password: "${PASS}"
        )
        rtUpload (
            serverId: "Artifactory-1",
            spec:
                """{
                "files": [
                    {
                    "pattern": "/home/Documents/simple-java-maven-app/auth.groovy",
                    "target": "Jenkins-integration/"
                    }
                ]
                }"""
        )
    }               
}