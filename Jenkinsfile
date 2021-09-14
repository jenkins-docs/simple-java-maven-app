//git凭证ID
def git_auth = "8f5ade86-95af-458f-b4fb-394a57813185"
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