#!/usr/bin/env bash
set -x
NAME=`mvn help:evaluate -Dexpression=project.name | grep "^[^\[]"`
echo `NAME: ${NAME}`
set -x
VERSION=`mvn help:evaluate -Dexpression=project.version | grep "^[^\[]"`
echo `VERSION: ${VERSION}`




