
# simple-java-maven-app

This repository is for the  
[Build a Java app with Maven](https://jenkins.io/doc/tutorials/build-a-java-app-with-maven/)  
tutorial in the [Jenkins User Documentation](https://jenkins.io/doc/).

It contains a simple Java application that prints **"Hello world!"** and includes unit tests to verify its functionality. Test results are saved as JUnit XML reports.

## 🐳 Docker Support (New)

This project is now Dockerized! You can easily build and run the app in an isolated container.

### Build the Docker image

```bash
docker build -t simple-java-maven-app .
```

### Run the Docker container

```bash
docker run simple-java-maven-app
```

Expected output:

```
Hello world!
```

## 📁 Jenkins Pipeline

The `jenkins/` directory contains:
- A `Jenkinsfile` for running the project in a Jenkins pipeline.
- A shell script in `jenkins/scripts/` that executes during the "Deliver" stage.

## 🧪 Testing

Run tests locally with:

```bash
mvn test
```

---

✅ PR raised to add Docker support. Happy to contribute to making this project cloud-ready and DevOps-friendly!

## 📌 Author

Contributed by Aakash Sharma

---

#Java #Maven #Docker #DevOps #OpenSource #Jenkins #CI #CloudNative
