@echo off
echo The following Maven command installs your Maven-built Java application
echo into the local Maven repository, which will ultimately be stored in
echo Jenkins's local Maven repository (and the "maven-repository" Docker data volume).

REM Run Maven install
mvn jar:jar install:install help:evaluate -Dexpression=project.name

echo The following command extracts the value of the <name/> element
echo within <project/> of your Java/Maven project's "pom.xml" file.

FOR /F "delims=" %%i IN ('mvn -q -DforceStdout help:evaluate -Dexpression=project.name') DO SET NAME=%%i

echo The following command behaves similarly to the previous one but
echo extracts the value of the <version/> element within <project/> instead.

FOR /F "delims=" %%i IN ('mvn -q -DforceStdout help:evaluate -Dexpression=project.version') DO SET VERSION=%%i

echo The following command runs and outputs the execution of your Java
echo application (which Jenkins built using Maven) to the Jenkins UI.

java -jar target\%NAME%-%VERSION%.jar
