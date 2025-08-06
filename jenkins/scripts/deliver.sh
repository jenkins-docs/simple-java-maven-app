#!/usr/bin/env bash

echo 'The following Maven command installs your Maven-built Java application'
echo 'into the local Maven repository, which will ultimately be stored in'
echo 'GitHub Actions''s local Maven repository (and the "maven-repository" Docker data'
echo 'volume).'
set -x
mvn jar:jar install:install help:evaluate -Dexpression=project.name
#mvn clean package
set +x

echo "==== JAR CLASS LIST ===="
jar tf target/${NAME}-${VERSION}.jar | grep App.class

echo "==== MANIFEST CONTENTS ===="
unzip -p target/${NAME}-${VERSION}.jar META-INF/MANIFEST.MF

echo 'The following command extracts the value of the <name/> element'
echo 'within <project/> of your Java/Maven project''s "pom.xml" file.'
set -x
NAME=`mvn -q -DforceStdout help:evaluate -Dexpression=project.name`
set +x

echo 'The following command behaves similarly to the previous one but'
echo 'extracts the value of the <version/> element within <project/> instead.'
set -x
VERSION=`mvn -q -DforceStdout help:evaluate -Dexpression=project.version`
set +x

echo 'The following command runs and outputs the execution of your Java'
echo 'application (which GitHub Actions built using Maven) to the GitHub Actions UI.'
set -x
java -jar target/${NAME}-${VERSION}.jar
