@Library('javapipeline-helpers') _

pipeline {
    agent none
    stages {
    stage ('Example') {
        steps {
             script { 
                 log.info 'Starting'
                 runpipeline 'karthi'
                 log.warning 'Nothing to do!'
                 }
            }
        }
    }
}

