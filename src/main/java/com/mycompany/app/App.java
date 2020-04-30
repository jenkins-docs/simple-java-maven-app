/*
Fuentes:

0) https://stackoverflow.com/questions/42525139/maven-build-compilation-error-failed-to-execute-goal-org-apache-maven-plugins
go to environment variables and add a new "system variable" called "JAVA_HOME" with a value "C:\Program Files\Java\jdk1.8.0_191"
Maven build Compilation error : Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.1:compile (default-compile) on project Maven

Aun ejecutando tan solo "mvn compile" marcaba el error Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.1:compile.
La causa fue que no estaba definida la variable de ambiente JAVA_HOME . Una vez que se agregó

Variable de ambiente (system variable)
JAVA_HOME=
C:\Program Files\Java\jdk1.8.0_231

-------------

1) https://stackoverflow.com/questions/57980136/how-to-run-java-program-using-maven
mvn compile exec:java -Dexec.mainClass=com.mycompany.app.App
mvn exec:java -Dexec.mainClass=com.mycompany.app.App

-------------

2) http://www.vineetmanohar.com/2009/11/3-ways-to-run-java-main-from-maven/
3 ways to run Java main from Maven

-------------

Otras fuentes:
https://www.vogella.com/tutorials/ApacheMaven/article.html

*/

package com.mycompany.app;

/**
 * Hello world!
 */
public class App
{

    private final String message = "Hello World.. 1..2..3..4..5..6..7";

    public App() {}

    public static void main(String[] args) {
        System.out.println(new App().getMessage());
    }

    //private final String getMessage() {
    private String getMessage() {
        return message;
    }

}
