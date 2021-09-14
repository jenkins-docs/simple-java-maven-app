//git凭证ID
def git_auth = "489e6b0f-9c46-4369-b7a0-64932c8cf52e"
//git的url地址,项目拉取地址
def git_url = "https://github.com/JiaoWoFeiFeiYa/simple-java-maven-app.git"
//镜像的版本号
def tag = "latest"
//创建node节点
node {
//代码拉取
    stage('拉取代码') {
    checkout([$class: 'GitSCM', branches: [[name: "*/${branch}"]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: "${git_auth}", url: "${git_url}"]]]
    }
}