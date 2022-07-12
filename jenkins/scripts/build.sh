#!/usr/bin/env bash

NAME=`mvn help:evaluate -Dexpression=project.name | grep "^[^\[]"`
echo `NAME: ${NAME}`
VERSION=`mvn help:evaluate -Dexpression=project.version | grep "^[^\[]"`
echo `VERSION: ${VERSION}`




