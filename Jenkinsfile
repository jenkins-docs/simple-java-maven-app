pipeline:
  agent:
    label: 'windows'  // Use a Windows agent

  environment:
    MAVEN_HOME: 'C:/Program Files/apache/maven'  // Path to Maven installation directory
    PATH: "$PATH:$MAVEN_HOME//bin"  // Add Maven bin directory to PATH

  stages:
    - stage: 'Install_Maven'
      steps:
        - script:
            name: 'Download and Install Maven'
            sh: |
              // Download Maven zip
              curl -O https://downloads.apache.org/maven/maven-3/3.8.4/binaries/apache-maven-3.8.4-bin.zip
              // Extract Maven zip
              Expand-Archive -Path 'C:\ProgramData\Jenkins\jenkins\workspace\Win-Pipeline\apache-maven-3.8.4-bin.zip' -DestinationPath 'C:\ProgramData\Jenkins\jenkins\workspace\Win-Pipeline\'
              // Move Maven to desired directory
              mv apache-maven-3.8.4 'C:\\Program Files\\Apache\\maven'

    - stage: Test
      steps:
        - script:
            name: 'Run Maven Test'
            sh: |
              // Navigate to the project directory
              cd 'C:\\Program Files\\Apache\\maven'
              # Run Maven test
              mvn test
