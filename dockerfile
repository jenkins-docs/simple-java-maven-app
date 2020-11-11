FROM anapsix/alpine-java
MAINTAINER gopi
WORKDIR /home/gopi/
COPY . /home/gopi/
CMD ["java","-jar","/home/gopi/my-app-1.0-SNAPSHOT.jar"]
