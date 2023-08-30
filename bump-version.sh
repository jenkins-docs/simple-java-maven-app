#!/bin/bash

current_version=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
new_version=$(echo $current_version | awk -F. '{$3++; OFS="."; print $0}')
mvn versions:set -DnewVersion=$new_version
git commit -am "Bump patch version"
git push origin main
