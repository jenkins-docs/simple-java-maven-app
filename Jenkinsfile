pipeline {
    agent {label 'fonify'}
 environment {
 mvn = '/usr/bin/mvn'
 }
 stages {
   stage('Get Current Branch') {
    steps {
		sh "echo Current Branch Name: ${env.BRANCH_NAME}"
     }
   }
   stage('Unit Test') {
    steps {
                sh 'ls -lrt'
		sh 'pwd'
     }
   }
   stage('Maven Clean Install') {
    when {
        branch 'feature'
    } 	
    steps {
       //sh 'mvn clean install'
	   sh 'pwd'
    }
   }
   stage('Build and push Docker Image') {
	when {
       branch 'develop'
    }
    steps {
         /*withAWS(credentials: '348dc31c-3c36-4e9a-84e1-8a3ad437b876', region: 'us-east-1') {
            sh '''
			 #!/bin/bash
			 IMAGE="dev-promotion-assessment"
			 NOW=`date +"%Y%m%d"`
			 BUILD="$BUILD_NUMBER"
			 TAG=$NOW.$BUILD
			 ECR_LOGINSERVER="186319575019.dkr.ecr.us-east-1.amazonaws.com"
			 aws ecr get-login-password | docker login --username AWS --password-stdin 186319575019.dkr.ecr.us-east-1.amazonaws.com
			 docker build -t ${IMAGE}:${TAG} .
			 docker images
			 docker tag ${IMAGE}:${TAG} ${ECR_LOGINSERVER}/${IMAGE}:${TAG}
			 docker push ${ECR_LOGINSERVER}/${IMAGE}:${TAG}
			 ''' 
        }*/
		sh 'pwd'	
     }
   }
   stage('Deploy on EKS cluster') {
	when {
       branch 'develop'
    }
     steps {
         /*withAWS(credentials: '348dc31c-3c36-4e9a-84e1-8a3ad437b876', region: 'us-east-1') {
            sh '''
            #!/bin/bash
            NOW=`date +"%Y%m%d"`
			BUILD="$BUILD_NUMBER"
			TAG=$NOW.$BUILD
			echo $TAG
            helm_path=/usr/local/bin
			RELEASE="pet-clinic"
			IMAGE="dev-promotion-assessment"
			NAMESPACE="default"
            aws eks --region us-east-1 update-kubeconfig --name Terraform-EKS-Cluster
            $helm_path/helm upgrade --install pet-clinic pet-clinic --set image.tag=$TAG
            '''
        }*/
		sh 'ls -lrt'
     }
   }
 }
 post {
    always {
 	echo 'clean up current workspace'
	deleteDir()
  }
 }
}
