//git凭证ID
def git_auth = "8f5ade86-95af-458f-b4fb-394a57813185"
//git的url地址
def git_url = "https://github.com/JiaoWoFeiFeiYa/simple-java-maven-app.git"
//镜像的版本号
def tag = "latest"
//定义harbor地址
//def harbor_url = "101.200.62.146:85"
//镜像库名称
//def harbor_project = "ceremony"
//harbor登录凭证ID
//def harbor_auth = "d4ae3419-62f8-42fb-bb59-5fc6c1d352f5"
node {
    stage('拉取代码') {
    checkout([$class: 'GitSCM', branches: [[name: "*/${branch}"]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: "${git_auth}", url: "${git_url}"]]])
    }
    stage('编译 安装公共实体bean') {
        sh "mvn clean install -Dmaven.test.skip=true"
    }
    stage('检查旧镜像,并对其执行删除操作') {
        //镜像名称
        def imageName = "${project_name}:${tag}"
        //删除本地镜像
        sh "docker rmi -f ${imageName}"
    }
    stage('工程编译') {
       //定义项目名称+镜像的版本号
        def imageName = "${project_name}:${tag}"
        sh "mvn  -f ${project_name} clean package -Dmaven.test.skip=true dockerfile:build "
        //对镜像打上标签
        sh "docker tag ${imageName} ${imageName}"
    }
    stage('docker部署') {
         //镜像名称
         def imageName = "${project_name}:${tag}"
         //删除原有容器
         sh "docker rm -f ${project_name}"
         sh "mkdir -p /opt/docker/${project_name}"
        //容器加一层挂载目录
        sh "docker run -di -v /opt/docker/${project_name}/opt:/opt --name ${project_name}  -p ${port}:${private_port} ${imageName}"
    }
    stage('启动并监控日志') {
       sh "docker logs -f ${project_name}"
    }
}