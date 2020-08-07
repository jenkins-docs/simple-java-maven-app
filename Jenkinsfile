pipeline {
    agent {
        docker {
            image 'maven:3-alpine' 
            args '-v /root/.m2:/root/.m2' 
        }
    }
    stages {
        stage('Build') { 
            steps {
                sh 'echo yujin' 
                sh 'mvn -B -gs maven-setting.xml -DskipTests clean package' 
                mail to: 'yujin19861013@163.com',
                     subject: "Failed Pipeline: ${env.BRANCH_NAME}",
                     body: "Something is DALIAN GOOD CITY wrong with ${env.WORKSPACE}"
            }
        }
	stage(‘docker构建镜像’){
         agent any
          steps {
          sh 'docker build -t icoding-java-img'
         }
      }
    
    stage('Ok'){
       agent any
           steps {
            withCredentials([usernamePassword(credentialsId: 'aliyun', passwordVariable: 'abcpwd', usernameVariable: 'user')]) {
            // some block
             sh 'docker login -u $user -p $abcpwd registry.cn-beijing.aliyuncs.com'
             sh 'docker tag icoding-java-img registry.cn-beijing.aliyuncs.com/jack_jin_namespace/yujin_docker:v1.0'
	     sh 'docker push registry.cn-beijing.aliyuncs.com/jack_jin_namespace/yujin_docker:v1.0'
             }
           }
    }
    }
}
