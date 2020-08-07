FROM java:8
#服务器只有dockerfile和jar在一起
COPY *.jar /app.jar
#即使运行没有-v，也会匿名挂载
VOLUME ["/logs"]

CMD ["--server.port=8080"]

EXPOSE ["8080"]

ENTRYPOINT ["java","-jar","/app.jar"]
