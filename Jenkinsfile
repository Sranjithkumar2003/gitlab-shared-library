pipeline {
	agent any
	stages {
	
		stage ('One') {
			steps {
				echo 'Hi, this is Ranjith'
			}
		}
		
		stage ('Build') {
			steps {
			    echo 'Running Build Pipeline'
			    sh './mvnw -Dmaven.test.failure.ignore=true clean package'
			    echo 'Completing Build Pipeline'
			}  
		}
		
		stage ('Test') {
		    steps {
			    echo 'Running Test Pipeline'
			}     
		}

	}
}