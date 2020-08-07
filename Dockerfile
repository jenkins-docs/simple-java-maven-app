FROM java:8
#服务器只有dockerfile和jar在一起
COPY target/*.jar /app.jar
#即使运行没有-v，也会匿名挂载
VOLUME ["/logs"]
ENTRYPOINT ["java","-jar","/app.jar"]
