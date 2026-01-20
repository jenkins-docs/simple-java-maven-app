# Authentication System - Web Browser Testing Guide

## Quick Start

### 1. Start the Web Server

Run the following command from the project root:

```bash
mvn compile exec:java -Dexec.mainClass="com.mycompany.app.web.WebServer"
```

### 2. Open Your Browser

Navigate to:
```
http://localhost:8080
```

### 3. Test the Authentication System

#### Sample Credentials
- **Username:** alice | **Password:** password123
- **Username:** bob | **Password:** securePass456
- **Username:** charlie | **Password:** myPassword789

## Features Available in Browser

### 🔐 Login
1. Enter username and password
2. Click "Login" button
3. Upon success, you'll see your session information

### 🚪 Logout
1. When logged in, click the "Logout" button
2. Your session will be terminated

### ✅ Check Status
1. Click "Check Status" to verify your authentication state
2. View your current session details

### 🔑 Password Reset

#### Initiate Reset
1. Enter a username in the "Password Reset" section
2. Click "Request Reset Token"
3. Copy the generated token (displayed on screen)

#### Complete Reset
1. Paste the reset token in the "Complete Password Reset" section
2. Enter your new password
3. Click "Reset Password"
4. Login with your new credentials

## Testing Scenarios

### Scenario 1: Successful Login Flow
1. Login as "alice" with password "password123"
2. Check status to verify authentication
3. Logout
4. Verify you're logged out

### Scenario 2: Invalid Credentials
1. Try logging in with wrong password
2. Observe the generic error message (security feature)
3. Try logging in with non-existent username
4. Notice the same generic error (prevents user enumeration)

### Scenario 3: Password Reset Flow
1. Request reset token for "bob"
2. Copy the token from the result page
3. Complete password reset with new password
4. Login with the new password
5. Success!

### Scenario 4: Session Management
1. Open two different browsers (e.g., Chrome and Firefox)
2. Login as different users in each browser
3. Verify each session is independent
4. Logout from one browser
5. Verify the other session remains active

## Stopping the Server

Press `Ctrl+C` in the terminal where the server is running.

## Alternative: CLI Demo

If you prefer the command-line interface, run:

```bash
mvn compile exec:java -Dexec.mainClass="com.mycompany.app.App"
```

This provides an interactive CLI menu with all authentication features.

## Technical Details

- **Server:** Embedded Jetty 11
- **Port:** 8080
- **Session Management:** HTTP Sessions
- **Security:** BCrypt password hashing, secure token generation
- **Architecture:** Servlet-based web application

## Troubleshooting

### Port Already in Use
If port 8080 is already in use, you'll see an error. Stop any other applications using port 8080 or modify the `PORT` constant in `WebServer.java`.

### Dependencies Not Found
Run `mvn clean install` to ensure all dependencies are downloaded.

### Browser Shows "Connection Refused"
Make sure the server is running and you're accessing `http://localhost:8080` (not https).
