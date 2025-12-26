                                                          Jenkins Production Assignment 2


<img width="523" height="199" alt="image" src="https://github.com/user-attachments/assets/710411e0-225a-4894-a6e7-e67e1ea37a57" />


##############################################################
pipeline {
    agent { label 'java-build-node' } // Scalability/Distributed requirement

    tools {
        maven 'M3' // Matches the name you set in Global Tool Configuration
    }

    options {
        retry(2) // Graceful failure management
        timeout(time: 15, unit: 'MINUTES')
    }

    environment {
        // Simulating a persistent cache location for Maven dependencies
        M2_CACHE = "/var/lib/jenkins/maven-cache" 
        PROD_CRED = credentials('prod-api-key') // Security requirement
    }

    stages {
        stage('Checkout') {
            steps {
                // Pulls code from your specific URL
                git 'https://github.com/Madhu427/simple-java-maven-app.git'
            }
        }

        stage('Build') {
            steps {
                echo "Building with Maven Caching..."
                // Use -Dmaven.repo.local to simulate logical caching of dependencies
                sh "mvn -Dmaven.repo.local=${M2_CACHE} clean compile"
            }
        }

        stage('Test') {
            steps {
                echo "Running Unit Tests..."
                sh "mvn -Dmaven.repo.local=${M2_CACHE} test"
            }
        }

        stage('Package') {
            steps {
                sh "mvn -Dmaven.repo.local=${M2_CACHE} package -DskipTests"
                // Archive the resulting JAR file
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            }
        }

        stage('Deploy (Simulated)') {
            steps {
                echo "Deploying JAR to production using ${PROD_CRED}..."
                // Logic check: ensure the JAR exists before "deploying"
                sh '[ -f target/*.jar ] && echo "Deployment Success" || exit 1'
            }
        }
    }

    post {
        failure {
            echo "Build failed. Alerting DevOps Team..."
        }
    }
}
#########################################################################

<img width="530" height="378" alt="image" src="https://github.com/user-attachments/assets/95ce7a46-115c-4a72-a833-c990d2947fa8" />

#################################################################
<img width="580" height="323" alt="image" src="https://github.com/user-attachments/assets/d3697433-adb0-47ad-9a6e-4b44a2786449" />



Step 1: Install and Configure Jenkins from Scratch
Since Docker is not allowed, we will perform a native installation.

Install Java (Prerequisite): Jenkins is a Java application. Install OpenJDK 17.

Bash

sudo apt update
sudo apt install fontconfig openjdk-17-jre -y
Add Jenkins Repository:

Bash

sudo wget -O /usr/share/keyrings/jenkins-keyring.asc \
  https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key
echo "deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc] \
  https://pkg.jenkins.io/debian-stable binary/" | sudo tee \
  /etc/apt/sources.list.d/jenkins.list > /dev/null
Install Jenkins:

Bash

sudo apt update
sudo apt install jenkins -y
Start and Enable Service:

Bash

sudo systemctl start jenkins
sudo systemctl enable jenkins
Step 2: Install and Manage Plugins via GUI
After accessing Jenkins for the first time at http://your-server-ip:8080, you need to manage your toolset.

Unlock Jenkins: Retrieve the initial password.

Bash

sudo cat /var/lib/jenkins/secrets/initialAdminPassword
Plugin Installation:

During the initial wizard, select "Select plugins to install."

Ensure Git and Pipeline are checked.

To manage later: Go to Manage Jenkins > Plugins > Available plugins to search for any missing tools.

Step 3: Secure Jenkins & Create Admin User
To restrict anonymous access and secure the environment:

Create First Admin: Complete the setup wizard by creating a username (e.g., jenkins_admin) and a strong password.

Verify Security Realm:

Go to Manage Jenkins > Security.

Ensure Security Realm is set to "Jenkins’ own user database."

Ensure Authorization is set to "Logged-in users can do anything" (temporarily, until Step 4).

Uncheck "Allow anonymous read access" to ensure only authorized users can see the dashboard.

Step 4: Enforce Role-Based Authorization
Standard production environments use the Role-based Authorization Strategy plugin.

Install Plugin: Go to Plugins and install "Role-based Authorization Strategy."

Change Strategy: Go to Manage Jenkins > Security > Authorization and select Role-Based Strategy. Save changes.

Manage Roles: Go to Manage Jenkins > Manage and Assign Roles > Manage Roles.

Admin Role: Give it all permissions.

Developer Role: Give it permissions like Overall:Read, Job:Build, Job:Read, and Job:Workspace.

Assign Roles: Go to Assign Roles and map your created Jenkins users to these specific roles.

Step 5: Enable Update Center and Verify Compatibility
Maintaining plugin health is critical for "production-ready" systems.

Configure Update Center:

Go to Manage Jenkins > Plugins > Advanced settings.

Verify the Update Site URL is correct (usually https://updates.jenkins.io/update-center.json).

Check for Updates: Go to the Updates tab in the Plugin Manager to see available security patches or newer versions.

Verify Compatibility: Before clicking "Install," check the "Compatibility" column. Jenkins will flag plugins that require a newer core version or have known security vulnerabilities.


<img width="667" height="301" alt="image" src="https://github.com/user-attachments/assets/21e8794c-47f4-4626-b871-f63eecfdba40" />


<img width="544" height="443" alt="image" src="https://github.com/user-attachments/assets/39897cf1-8a09-4320-8b6b-ecbd6e383e6c" />

#################################################

<img width="566" height="222" alt="image" src="https://github.com/user-attachments/assets/d3d69d52-cfa9-4fa1-8c97-03c4fcaef56c" />



Follow these steps for both required jobs: build-and-test-service-A and build-and-test-service-B.

Create Jenkins Jobs:

Select New Item on the dashboard, enter the specific job name (e.g., build-and-test-service-A), and select  "Pipeline".

Configure Git Branching:

In the Source Code Management section, provide your Git repository URL.

Crucially, configure each job to clone from a different branch (e.g., Job A clones the dev branch, while Job B clones the feature branch).

Simulate Unit Tests:

Add an Execute Shell build step.

Run basic shell script logic such as echo "Running unit tests..." followed by a conditional check to simulate a testing environment.

Generate and Save Build Artifacts:

In the same shell script, include a command to save build artifacts by creating a compressed file, such as tar -czvf service-a-build.tar.gz ./*.

Ensure these fake .tar.gz files are generated directly within the Jenkins workspace.

Archive Build Artifacts:

Add a Post-build Action titled Archive the artifacts.

Specify the file pattern (e.g., *.tar.gz) to ensure the build output is preserved and accessible from the Jenkins job dashboard

Jenkinsfile
---------------------------------------
pipeline {
    agent any

    stages {
        stage('Checkout Source') {
            steps {
                // Task: Source Code Management with Git
                // For Job A use 'dev', for Job B use 'feature'
                git branch: 'feature', url: 'https://github.com/Madhu427/simple-java-maven-app.git'
            }
        }

        stage('Build and Simulate Tests') {
            steps {
                // Task: Run basic shell script logic to simulate unit tests
                sh '''
                    #!/bin/bash
                    echo "Starting Build and Test Process..."
                    
                    # Simulate Test Result
                    TEST_RESULT=0 
                    
                    if [ $TEST_RESULT -eq 0 ]; then
                        echo "Unit Tests Passed Successfully!"
                    else
                        echo "Unit Tests Failed!"
                        exit 1
                    fi

                    # Task: Save build artifacts (fake .tar.gz files) in workspace
                    echo "Generating Build Version: 1.0.$BUILD_NUMBER" > build_info.txt
                    tar -czvf service-build-${BUILD_NUMBER}.tar.gz build_info.txt
                '''
            }
        }
    }

    post {
        always {
            // Task: Archive build artifacts
            // This ensures *.tar.gz files are saved and visible on the job page
            archiveArtifacts artifacts: '*.tar.gz', fingerprint: true
        }
    }
}
---------------------------------------
<img width="596" height="419" alt="image" src="https://github.com/user-attachments/assets/9e01a1c2-7975-4e80-86c9-9caf017015d4" />


<img width="653" height="443" alt="image" src="https://github.com/user-attachments/assets/d7be20d0-2236-4f97-8954-d995f871d6ae" />

<img width="713" height="240" alt="image" src="https://github.com/user-attachments/assets/4036b38f-72c5-443e-ba26-5cd5df464d42" />

JOB B

<img width="603" height="410" alt="image" src="https://github.com/user-attachments/assets/ad44faeb-e817-4caf-b27b-8cb5f4fbe1fc" />

<img width="546" height="463" alt="image" src="https://github.com/user-attachments/assets/4bc776f8-43cc-4bf3-b73f-581289ca6842" />

<img width="515" height="260" alt="image" src="https://github.com/user-attachments/assets/62d2100e-23d6-449c-8c8b-2d6060fa1c32" />


######################################################################################

<img width="554" height="143" alt="image" src="https://github.com/user-attachments/assets/1e19fe2f-de6c-4b68-af96-3a495415cebd" />


----------------------------------------------------------------------
pipeline {
    agent any
    
    // Task 3: Use parameterized jobs for dynamic selection
    parameters {
        string(name: 'BRANCH_NAME', defaultValue: 'master', description: 'Branch selection (e.g., master or feature)')
        booleanParam(name: 'DEBUG_MODE', defaultValue: false, description: 'Debug mode toggle')
        choice(name: 'ENVIRONMENT', choices: ['dev', 'staging', 'prod'], description: 'Simulated environment selection')
    }

    stages {
        stage('Checkout Source') {
            steps {
                // Task: Source Code Management with Git
                // Uses the BRANCH_NAME parameter for dynamic cloning
                git branch: "${params.BRANCH_NAME}", url: 'https://github.com/Madhu427/simple-java-maven-app.git'
            }
        }

        stage('Build and Simulate Tests') {
            steps {
                // Task: Run basic shell script logic to simulate unit tests
                sh '''
                    #!/bin/bash
                    echo "Starting Build on Environment: ${ENVIRONMENT}"
                    
                    # Check Debug Mode toggle
                    if [ "${DEBUG_MODE}" = "true" ]; then
                        echo "DEBUG: System path is $PATH"
                        set -x # Enable verbose logging
                    fi

                    # Simulate Test Result
                    TEST_RESULT=0 
                    
                    if [ $TEST_RESULT -eq 0 ]; then
                        echo "Unit Tests Passed Successfully!"
                    else
                        echo "Unit Tests Failed!"
                        exit 1
                    fi

                    # Task: Save build artifacts (fake .tar.gz files) in workspace
                    echo "Generating Build Version: 1.0.$BUILD_NUMBER" > build_info.txt
                    echo "Environment: ${ENVIRONMENT}" >> build_info.txt
                    tar -czvf service-build-${BUILD_NUMBER}.tar.gz build_info.txt
                '''
            }
        }
    }

    post {
        always {
            // Task: Archive build artifacts
            // This ensures *.tar.gz files are saved and visible on the job page
            archiveArtifacts artifacts: '*.tar.gz', fingerprint: true
        }
    }
}

--------------------------------------------------

<img width="874" height="349" alt="image" src="https://github.com/user-attachments/assets/4e81a0b5-47ba-469b-8158-0d7c4b2aa825" />

<img width="704" height="455" alt="image" src="https://github.com/user-attachments/assets/6d45b8dc-83a5-42a1-a031-027798b43423" />

<img width="617" height="297" alt="image" src="https://github.com/user-attachments/assets/41e3d0bf-3d00-462a-b7b5-a55c49541a3c" />



