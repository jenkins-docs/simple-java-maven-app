![Sample SVG](https://github.com/Joska99/simple-java-maven-app/blob/master/diagram.drawio.svg)

[![CI - Build and Push Docker Image](https://github.com/Joska99/simple-java-maven-app/actions/workflows/docker-ci.yml/badge.svg)](https://github.com/Joska99/simple-java-maven-app/actions/workflows/docker-ci.yml)
<br />

[![CD - Deploy Docker container to EC2 with Ansible](https://github.com/Joska99/simple-java-maven-app/actions/workflows/deploy-docker.yml/badge.svg)](https://github.com/Joska99/simple-java-maven-app/actions/workflows/deploy-docker.yml)
<br />

# Requirements:

- EC2 instance
- SSH key for EC2 instance
- DockerHub Repository
- Repository secrets

# Provide secrets:

1. Settings -> Secrets and Variables -> Actions -> Secrets -> New Repository secret
2. Provide those secrets:
   > DOCKERHUB_PSWD  
   > DOCKERHUB_USER\
   > EC2_IP\
   > EC2_USER\
   > SSH_KEY_JOSKA_EC2\
   > SSH_PORT\
   > GITHUB_TOKEN
