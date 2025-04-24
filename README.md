# Java Maven Tomcat Application

This project is a simple Java web application built using Maven and deployed on Tomcat. Below are the details regarding the setup, usage, and other relevant information.

## Project Structure

```
java-maven-tomcat-app
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── App.java
│   │   ├── resources
│   │   │   └── application.properties
│   │   └── webapp
│   │       ├── WEB-INF
│   │       │   └── web.xml
│   │       └── index.jsp
│   └── test
│       └── java
│           └── com
│               └── example
│                   └── AppTest.java
├── pom.xml
├── Jenkinsfile
└── README.md
```

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- Apache Maven
- Apache Tomcat
- Jenkins

## Setup Instructions

1. **Clone the Repository**
   ```
   git clone <repository-url>
   cd java-maven-tomcat-app
   ```

2. **Build the Application**
   Use Maven to build the application:
   ```
   mvn clean install
   ```

3. **Deploy to Tomcat**
   Copy the generated WAR file from the `target` directory to the Tomcat `webapps` directory.

4. **Start Tomcat**
   Start the Tomcat server and access the application at `http://localhost:8080/java-maven-tomcat-app`.

## Usage

Once the application is deployed, you can access the main page through your web browser. The application may include various functionalities as defined in the `App.java` class.

## Running Tests

To run the unit tests, execute the following command:
```
mvn test
```

## Jenkins Integration

The project includes a `Jenkinsfile` for continuous integration and deployment. Configure Jenkins to use this file for building and deploying the application automatically.

## License

This project is licensed under the MIT License - see the LICENSE file for details.