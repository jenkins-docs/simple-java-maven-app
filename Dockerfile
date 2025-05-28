# 基础镜像（推荐小巧稳定）
FROM harbor.idc.roywong.work/docker.io/library/eclipse-temurin:17-jre-alpine

# 镜像作者信息（可选）
LABEL maintainer="whh881114@gmail.com" \
      description="the first java application demo"

# 设置工作目录
WORKDIR /app

# 拷贝 jar 包
COPY target/app.jar app.jar

# 启动命令
ENTRYPOINT ["java", "-jar", "app.jar"]
