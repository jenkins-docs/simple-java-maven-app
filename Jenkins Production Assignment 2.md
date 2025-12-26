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





