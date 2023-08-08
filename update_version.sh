#!/bin/bash

curr_version=`mvn help:evaluate -Dexpression=project.version -q -DforceStdout`
parts=( ${curr_version//./ } )
string=( ${curr_version//-/ } )
bv=$((parts[2] + 1))

if [[ -z ${string[1]} ]]; then
  NEW_VERSION="${parts[0]}.${parts[1]}.${bv}"
else
  NEW_VERSION="${parts[0]}.${parts[1]}.${bv}-${string[1]}"
fi

echo $NEW_VERSION
