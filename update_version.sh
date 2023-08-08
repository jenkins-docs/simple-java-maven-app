#!/bin/bash

current_version=$(grep -oP '(?<=<version>)(\d+\.\d+\.)(\d+)(?=<\/version>)' pom.xml)
new_patch=$((current_version + 1))
updated_version=$(echo "$current_version$new_patch")

sed -i "s/<version>$current_version<\/version>/<version>$updated_version<\/version>/" pom.xml

