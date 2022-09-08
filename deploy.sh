#!/bin/bash
sudo docker rm -f springhello
sudo docker login -u admin -p admin123 http://54.88.21.164:8082
sudo docker run -d --name springhello 
