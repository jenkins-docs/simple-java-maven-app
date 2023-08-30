#!/bin/bash
# Define Docker image details
DOCKER_IMAGE="roybidani/simple-java-maven-app:${{ github.sha }}"
# Pull the latest Docker image
docker pull $DOCKER_IMAGE
# Stop and remove the existing container (if it exists)
docker stop simple-java-maven-app || true
docker rm simple-java-maven-app || true
# Run the Docker container
docker run -d --name simple-java-maven-app -p 80:8080 $DOCKER_IMAGE



