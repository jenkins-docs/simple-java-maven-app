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
}