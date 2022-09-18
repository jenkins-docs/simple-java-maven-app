pipeline {
	  agent any
	  parameters {
	    choice(name: 'door_choice',
	      choices: 'one\ntwo\nthree\nfour',
	      description: 'What door do you choose?')
	    booleanParam(name: 'CAN_DANCE',
	      defaultValue: true,
	      description: 'Checkbox parameter')
	    string(name: 'sTrAnGePaRaM',
	      defaultValue: 'Dance!',
	      description: 'Do the funky chicken!')
	  }
	  stages {
	    stage('Example') {
	      steps {
	        echo 'Hello World!'
	        echo "Trying: ${params.door_choice}"
	        echo "We can dance: ${params.CAN_DANCE}"
	        echo "The DJ says: ${params.sTrAnGePaRaM}"
	      }
	    }
	  }
	}
