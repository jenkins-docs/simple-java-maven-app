node {
    stage('Build') { 
        mvn('clean install')
        withCredentials([usernameColonPassword(credentialsId: 'fruity', variable: 'USERPASS')]) {
            def method = load("auth.groovy")
            method.auth(USERPASS)
        }
    }
    stage('Tester') {
        mvn('test')
        junit 'target/surefire-reports/*.xml'
    }
    stage('SonarQube analysis') {
        scanner = tool 'Scanner' 
        withSonarQubeEnv('Sonarqube') {
            sh "${scanner}/bin/sonar-scanner"
        }
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
}

def mvn(args) {
    def mvn_version = 'M3'
    withEnv( ["PATH+MAVEN=${tool mvn_version}/bin"] ) {
        sh "mvn $args" 
    }
}