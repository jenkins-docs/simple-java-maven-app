pipeline{
	agent any
	tools {
		maven "maven"
	}
	options{
		timeout(time: 1, unit: "HOURS")
		timestamps()
		disableConcurrentBuilds()
	}
	environment{
		MY_CRED = credentials("TEST")
	}
	parameters{
		choice(name: "Branch", choices: ["Dev", "Test", "Prod"], description: "Declare the branch")
		booleanParam(name: "Test", defaultValue: true, description: "Want to test the code?")
	}
	stages{
		stage("Build") {
			steps{
				script {
					def name = params.Branch
					if (name == "Dev"){
						sh "mvn clean package"
						println "My creds is ${MY_CRED}"
					}
				}
			}
		}
		stage("Test") {
			when {
				expression {
					params.Test == true
				}
			}
			steps {
				sh "mvn test"
			}
			post {
				success {
					archiveArtifacts artifacts: "**/target/*.jar"
				}
			}
		}
		stage("parallel demo") {
			parallel{
				stage("First"){
					steps{
						retry(3){
							echo "First parallel step"
							sleep 10
							error "Throwing an error"
						}
					}
				}
				stage("Second"){
					steps{
						echo "Second parallel step"
						sleep 10
					}
				}
			}
		}
	}
}
