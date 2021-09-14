//git凭证ID
def git_auth = "ab0f332e-16d4-4950-b643-56723e49ad25"
//git的url地址,项目拉取地址
def git_url = "https://gitee.com/zhang-wangfei/leetcode.git"
//镜像的版本号
def tag = "latest"
//创建node节点
node {
    stage('拉取代码') {
    checkout([$class: 'GitSCM', branches: [[name: "*/${branch}"]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: "$ {git_auth}", url: "$/{git_url}"]]]
