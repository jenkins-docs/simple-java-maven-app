#!/bin/bash

current_version=$(awk -F'[<>]' '/<artifactId>my-app<\/artifactId>/{getline; getline; print $3}' pom.xml)

IFS='.-' read -ra version_parts <<< "$current_version"
major_version="${version_parts[0]}"
minor_version="${version_parts[1]}"
patch_version="${version_parts[2]}"

patch_version=$((patch_version + 1))

new_version="$major_version.$minor_version.$patch_version-SNAPSHOT"

sed -i "s/<version>$current_version<\/version>/<version>$new_version<\/version>/" pom.xml
