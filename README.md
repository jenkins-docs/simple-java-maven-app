# simple-java-maven-app

This repository is for the
[Build a Java app with Maven](https://jenkins.io/doc/tutorials/build-a-java-app-with-maven/)
tutorial in the [Jenkins User Documentation](https://jenkins.io/doc/).

The repository contains a simple Java application which outputs the string
"Hello world!" and is accompanied by a couple of unit tests to check that the
main application works as expected. The results of these tests are saved to a
JUnit XML report.

The `jenkins` directory contains an example of the `Jenkinsfile` (i.e. Pipeline)
you'll be creating yourself during the tutorial and the `jenkins/scripts` subdirectory
contains a shell script with commands that are executed when Jenkins processes
the "Deliver" stage of your Pipeline.
# 
---- 

# Email Notification
There are 2 plugins to send email notification. Email Notification and Extended Email. We will prefer to use Email extended as it provides more features and control on Email notification.

Follow below steps to configure and send email notification

## Setup Google Account to generate App Password
For integrating google email with jenkins, we need to generate a App password. The normal password for gmail will not work with integration. 
To generate google app password, we need to enable 2 MFA. Please follow below steps to generate a app password.

```
- Navigate to Google account --> Profile Pic --> Manage Account
- Select Security and Enable 2 factor authentication if you have not done it
	○ You can follow instruction and choose different option instead of phone number
	○ It will give you option to prompt on your device after login
	○ If you device doesn't appear on page, then Add your google account in your mobile phone
- Once 2MFA is enable navigate back to security Tab and click on 2 Step Verification in Right Pane
- On bottom of the page you will find App Password
- Create a new App password for Jenkins
```
## Setup Jenkins Email Extended Plugin
Navigate to Manage Jenkins--> System--> Extended Email and add following details

```
smtp server : smtp.gmail.com
Smtp port : 465
- Advance
	□ Add Credentials for Google username and App password generated above
	□ Use SSL
Default user email suffix : @gmail.com
ListID : Leave it blank
Default Recipients : vcjain.training1@gmail.com
Reply To List : noreply@gmail.com
Leave everything as Default
Chose Appropriate Default Triggers
```

## Setup Jenkins Email Notification Plugin

Navigate to Manage Jenkins--> System--> Email Notification and add following details
```
smtp server : smtp.gmail.com
Default user email suffix : @gmail.com
- Advance 
Use SMTP Authentication
Add Credentials for Google username and App password generated above
Use SSL
SMTP Port : 465
Reply To List : noreply@gmail.com
Charset : UTF-8 (by default it is UTF-8)
Test e-mail recipient : <enter recipient email id >
```
## Configure Job to send Email
- Add Extended Email as post Build action to receive emails
- Select Always Triggers


-----
