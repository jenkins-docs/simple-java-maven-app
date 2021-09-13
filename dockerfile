#FROM java:8 设置基础镜像
FROM openjdk:8-jdk-alpine
# 传递参数,微服务打包的jar包路径,如上配置的JAR_FILE标签中取值
ARG JAR_FILE
#将jar包copy到容器中
COPY ${JAR_FILE} app.jar
# 抛出端口,可有可无
EXPOSE 9603
# 在容器内执行发布命令
CMD ["nohup","java","-jar","/app.jar","&"]
`