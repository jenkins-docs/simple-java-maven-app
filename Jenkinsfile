pipeline{
	agent any
	tools {
		maven "Maven"
	}
	options{
		timeout(time: 1, unit: "HOURS")
		timestamps()
		skipDefaultCheckout()
		disableConcurrentBuilds()
	}
	triggers{
		pollSCM("H/5 * * * *")
	}
	credentials{
		MY_CRED = credentials("TEST")
	}
	parameters{
		choice(name: "Branch", choices: ["Dev", "Test", "Prod"], description: "Declare the branch")
		booleanParam(name: "Test", defaultValue: true, description: "Want to test the code?")
	}
	stages{
		stage("Checkout code"){
			steps{
				checkout scm
			}
		}
		stage("Build") {
			steps{
				script {
					def name = $Branch
					if ($name == "Dev"){
						sh "mvn clean package"
						echo "My creds is $MY_CRED"
					}
				}
			}
		}
		stage("Test") {
			when {
				expression {
					$Test == true
				}
			}
			steps {
				sh "mvn test"
			}
			post {
				success {
					archieveArtifacts artifacts: "**/target/*.jar"
				}
			}
		}
		stage("Parellel demo") {
			parellel{
				steps{
					retry(3){
						echo "First parellel step"
						sleep 10
						error "Throwing an error"
					}
				}
				steps{
					echo "Second parellel step"
					sleep 10
				}
			}
		}
	}
}
