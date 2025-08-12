@echo off
echo The following Maven command installs your Maven-built Java application
echo into the local Maven repository...
mvn jar:jar install:install help:evaluate -Dexpression=project.name

for /f %%i in ('mvn -q -DforceStdout help:evaluate -Dexpression=project.name') do set NAME=%%i
for /f %%i in ('mvn -q -DforceStdout help:evaluate -Dexpression=project.version') do set VERSION=%%i

java -jar target\%NAME%-%VERSION%.jar
