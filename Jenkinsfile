stage("Build") {
    agent { label 'built-in' }  // Windows master
    steps {
        bat "mvn clean package"
        stash includes: 'target/*.jar', name: 'myAppJar'
    }
}

stage("Run on Slaves") {
    parallel {
        stage("Run on Slave1") {
            agent { label 'slave1' }
            steps {
                unstash 'myAppJar'
                sh "java -cp target/my-app-1.0-SNAPSHOT.jar com.mycompany.app.App"
            }
        }
        stage("Run on Slave2") {
            agent { label 'slave2' }
            steps {
                unstash 'myAppJar'
                sh "java -cp target/my-app-1.0-SNAPSHOT.jar com.mycompany.app.App"
            }
        }
    }
}
