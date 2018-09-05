FROM openjdk:8-jdk-alpine

RUN apk add --no-cache curl tar bash procps git openssh-client ttf-dejavu coreutils tini unzip sudo openrc shadow docker

