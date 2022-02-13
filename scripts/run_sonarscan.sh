cd /Users/jig/Downloads/sonar-scanner-4.6.2.2472-macosx

./sonar-scanner \
  -Dsonar.projectKey=java-maven-app \
  -Dsonar.sources=. \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=a7e299234d0c83bcf1dfb733b42e408b7388468b