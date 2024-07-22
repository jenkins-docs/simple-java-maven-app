FROM openjdk
WORKDIR /usr/app
COPY ./target/my-app-1.0-SNAPSHOT.jar /usr/app
ENTRYPOINT [ "java","-jar", "my-app-1.0-SNAPSHOT.jar" ]
EXPOSE 8080