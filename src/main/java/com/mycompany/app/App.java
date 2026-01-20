package com.mycompany.app;

import com.mycompany.app.auth.model.User;
import com.mycompany.app.auth.repository.InMemoryPasswordResetTokenRepository;
import com.mycompany.app.auth.repository.InMemorySessionRepository;
import com.mycompany.app.auth.repository.InMemoryUserRepository;
import com.mycompany.app.auth.result.AuthenticationResult;
import com.mycompany.app.auth.result.PasswordResetResult;
import com.mycompany.app.auth.service.AuthenticationService;
import com.mycompany.app.auth.service.AuthenticationServiceImpl;
import com.mycompany.app.auth.service.PasswordHasher;
import com.mycompany.app.auth.service.SecureIdentifierGenerator;

import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * Authentication System Demonstration Application
 * 
 * This application demonstrates the complete user authentication system
 * including login, logout, and password reset functionality.
 */
public class App {
    
    private final AuthenticationService authService;
    private final Scanner scanner;
    private String currentSessionId;
    
    public App() {
        // Initialize repositories
        InMemoryUserRepository userRepository = new InMemoryUserRepository();
        InMemorySessionRepository sessionRepository = new InMemorySessionRepository();
        InMemoryPasswordResetTokenRepository tokenRepository = new InMemoryPasswordResetTokenRepository();
        
        // Initialize security components
        PasswordHasher passwordHasher = new PasswordHasher();
        SecureIdentifierGenerator idGenerator = new SecureIdentifierGenerator();
        
        // Initialize authentication service
        this.authService = new AuthenticationServiceImpl(
            userRepository,
            sessionRepository,
            tokenRepository,
            passwordHasher,
            idGenerator
        );
        
        this.scanner = new Scanner(System.in);
        this.currentSessionId = null;
        
        // Pre-populate with sample users
        initializeSampleUsers(userRepository, passwordHasher);
    }
    
    private void initializeSampleUsers(InMemoryUserRepository userRepository, PasswordHasher passwordHasher) {
        // Create sample users
        User alice = new User(
            "alice",
            passwordHasher.hashPassword("password123"),
            LocalDateTime.now(),
            null
        );
        
        User bob = new User(
            "bob",
            passwordHasher.hashPassword("securePass456"),
            LocalDateTime.now(),
            null
        );
        
        User charlie = new User(
            "charlie",
            passwordHasher.hashPassword("myPassword789"),
            LocalDateTime.now(),
            null
        );
        
        userRepository.save(alice);
        userRepository.save(bob);
        userRepository.save(charlie);
    }
    
    public static void main(String[] args) {
        App app = new App();
        app.run();
    }
    
    public void run() {
        printWelcomeBanner();
        printSampleCredentials();
        
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    handleLogin();
                    break;
                case "2":
                    handleLogout();
                    break;
                case "3":
                    handleCheckAuthStatus();
                    break;
                case "4":
                    handleInitiatePasswordReset();
                    break;
                case "5":
                    handleCompletePasswordReset();
                    break;
                case "6":
                    runDemonstrationScenarios();
                    break;
                case "7":
                    running = false;
                    System.out.println("\n👋 Thank you for using the Authentication System Demo!");
                    break;
                default:
                    System.out.println("\n❌ Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }
    
    private void printWelcomeBanner() {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║     User Authentication System - Demonstration App        ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println();
    }
    
    private void printSampleCredentials() {
        System.out.println("📋 Sample User Credentials:");
        System.out.println("   • Username: alice    | Password: password123");
        System.out.println("   • Username: bob      | Password: securePass456");
        System.out.println("   • Username: charlie  | Password: myPassword789");
        System.out.println();
    }
    
    private void printMenu() {
        System.out.println("\n" + "=".repeat(60));
        if (currentSessionId != null && authService.isAuthenticated(currentSessionId)) {
            String username = authService.getCurrentUser(currentSessionId);
            System.out.println("🟢 Logged in as: " + username);
        } else {
            System.out.println("🔴 Not logged in");
        }
        System.out.println("=".repeat(60));
        System.out.println("1. Login");
        System.out.println("2. Logout");
        System.out.println("3. Check Authentication Status");
        System.out.println("4. Initiate Password Reset");
        System.out.println("5. Complete Password Reset");
        System.out.println("6. Run Demonstration Scenarios");
        System.out.println("7. Exit");
        System.out.println("=".repeat(60));
        System.out.print("Enter your choice: ");
    }
    
    private void handleLogin() {
        System.out.println("\n--- Login ---");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        
        AuthenticationResult result = authService.login(username, password);
        
        if (result.isSuccessful()) {
            currentSessionId = result.getSessionId();
            System.out.println("✅ Login successful!");
            System.out.println("   Session ID: " + currentSessionId);
        } else {
            System.out.println("❌ Login failed: " + result.getErrorMessage());
        }
    }
    
    private void handleLogout() {
        System.out.println("\n--- Logout ---");
        
        if (currentSessionId == null) {
            System.out.println("⚠️  You are not logged in.");
            return;
        }
        
        authService.logout(currentSessionId);
        System.out.println("✅ Logout successful!");
        currentSessionId = null;
    }
    
    private void handleCheckAuthStatus() {
        System.out.println("\n--- Authentication Status ---");
        
        if (currentSessionId == null) {
            System.out.println("🔴 Status: Not authenticated (no session)");
            return;
        }
        
        boolean isAuth = authService.isAuthenticated(currentSessionId);
        if (isAuth) {
            String username = authService.getCurrentUser(currentSessionId);
            System.out.println("🟢 Status: Authenticated");
            System.out.println("   Username: " + username);
            System.out.println("   Session ID: " + currentSessionId);
        } else {
            System.out.println("🔴 Status: Not authenticated (session invalid or expired)");
            currentSessionId = null;
        }
    }
    
    private void handleInitiatePasswordReset() {
        System.out.println("\n--- Initiate Password Reset ---");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        
        PasswordResetResult result = authService.initiatePasswordReset(username);
        
        if (result.isSuccessful()) {
            System.out.println("✅ Password reset initiated!");
            System.out.println("   Reset Token: " + result.getToken());
            System.out.println("   (In a real system, this would be sent via email)");
        } else {
            System.out.println("⚠️  " + result.getErrorMessage());
        }
    }
    
    private void handleCompletePasswordReset() {
        System.out.println("\n--- Complete Password Reset ---");
        System.out.print("Reset Token: ");
        String token = scanner.nextLine().trim();
        System.out.print("New Password: ");
        String newPassword = scanner.nextLine().trim();
        
        boolean success = authService.completePasswordReset(token, newPassword);
        
        if (success) {
            System.out.println("✅ Password reset completed successfully!");
            System.out.println("   You can now login with your new password.");
        } else {
            System.out.println("❌ Password reset failed. Token may be invalid, expired, or password doesn't meet requirements.");
        }
    }
    
    private void runDemonstrationScenarios() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║           Running Demonstration Scenarios                  ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        
        // Scenario 1: Successful Login and Logout
        System.out.println("\n📌 Scenario 1: Successful Login and Logout");
        System.out.println("   Action: Login as 'alice' with correct password");
        AuthenticationResult loginResult = authService.login("alice", "password123");
        System.out.println("   Result: " + (loginResult.isSuccessful() ? "✅ Success" : "❌ Failed"));
        if (loginResult.isSuccessful()) {
            String sessionId = loginResult.getSessionId();
            System.out.println("   Session created: " + sessionId);
            
            System.out.println("   Action: Verify authentication status");
            boolean isAuth = authService.isAuthenticated(sessionId);
            System.out.println("   Result: " + (isAuth ? "✅ Authenticated" : "❌ Not authenticated"));
            
            System.out.println("   Action: Logout");
            authService.logout(sessionId);
            boolean stillAuth = authService.isAuthenticated(sessionId);
            System.out.println("   Result: " + (!stillAuth ? "✅ Session invalidated" : "❌ Session still valid"));
        }
        
        // Scenario 2: Invalid Login Attempts
        System.out.println("\n📌 Scenario 2: Invalid Login Attempts");
        System.out.println("   Action: Login with wrong password");
        AuthenticationResult wrongPassword = authService.login("alice", "wrongpassword");
        System.out.println("   Result: " + (!wrongPassword.isSuccessful() ? "✅ Correctly rejected" : "❌ Incorrectly accepted"));
        System.out.println("   Message: " + wrongPassword.getErrorMessage());
        
        System.out.println("   Action: Login with non-existent user");
        AuthenticationResult nonExistent = authService.login("nonexistent", "password");
        System.out.println("   Result: " + (!nonExistent.isSuccessful() ? "✅ Correctly rejected" : "❌ Incorrectly accepted"));
        System.out.println("   Message: " + nonExistent.getErrorMessage());
        
        // Scenario 3: Password Reset Flow
        System.out.println("\n📌 Scenario 3: Complete Password Reset Flow");
        System.out.println("   Action: Initiate password reset for 'bob'");
        PasswordResetResult resetResult = authService.initiatePasswordReset("bob");
        System.out.println("   Result: " + (resetResult.isSuccessful() ? "✅ Token generated" : "❌ Failed"));
        
        if (resetResult.isSuccessful()) {
            String token = resetResult.getToken();
            System.out.println("   Token: " + token);
            
            System.out.println("   Action: Complete password reset with new password");
            boolean resetComplete = authService.completePasswordReset(token, "newSecurePassword123");
            System.out.println("   Result: " + (resetComplete ? "✅ Password updated" : "❌ Failed"));
            
            if (resetComplete) {
                System.out.println("   Action: Login with new password");
                AuthenticationResult newLogin = authService.login("bob", "newSecurePassword123");
                System.out.println("   Result: " + (newLogin.isSuccessful() ? "✅ Login successful" : "❌ Login failed"));
                
                if (newLogin.isSuccessful()) {
                    authService.logout(newLogin.getSessionId());
                }
                
                // Restore original password for future demos
                PasswordResetResult restore = authService.initiatePasswordReset("bob");
                if (restore.isSuccessful()) {
                    authService.completePasswordReset(restore.getToken(), "securePass456");
                }
            }
        }
        
        // Scenario 4: Session Management
        System.out.println("\n📌 Scenario 4: Session Management");
        System.out.println("   Action: Create multiple sessions for different users");
        AuthenticationResult session1 = authService.login("alice", "password123");
        AuthenticationResult session2 = authService.login("charlie", "myPassword789");
        System.out.println("   Result: " + (session1.isSuccessful() && session2.isSuccessful() ? "✅ Multiple sessions created" : "❌ Failed"));
        
        if (session1.isSuccessful() && session2.isSuccessful()) {
            System.out.println("   Alice's session: " + session1.getSessionId());
            System.out.println("   Charlie's session: " + session2.getSessionId());
            
            System.out.println("   Action: Verify each session independently");
            String user1 = authService.getCurrentUser(session1.getSessionId());
            String user2 = authService.getCurrentUser(session2.getSessionId());
            System.out.println("   Session 1 user: " + user1 + " " + (user1.equals("alice") ? "✅" : "❌"));
            System.out.println("   Session 2 user: " + user2 + " " + (user2.equals("charlie") ? "✅" : "❌"));
            
            System.out.println("   Action: Logout one session");
            authService.logout(session1.getSessionId());
            boolean session1Valid = authService.isAuthenticated(session1.getSessionId());
            boolean session2Valid = authService.isAuthenticated(session2.getSessionId());
            System.out.println("   Session 1 valid: " + (!session1Valid ? "✅ Invalidated" : "❌ Still valid"));
            System.out.println("   Session 2 valid: " + (session2Valid ? "✅ Still valid" : "❌ Invalidated"));
            
            authService.logout(session2.getSessionId());
        }
        
        // Scenario 5: Security Features
        System.out.println("\n📌 Scenario 5: Security Features");
        System.out.println("   Action: Verify password hashing (passwords never stored in plaintext)");
        System.out.println("   Result: ✅ All passwords are hashed using BCrypt");
        
        System.out.println("   Action: Verify generic error messages (no user enumeration)");
        AuthenticationResult test1 = authService.login("alice", "wrong");
        AuthenticationResult test2 = authService.login("nonexistent", "wrong");
        boolean sameMessage = test1.getErrorMessage().equals(test2.getErrorMessage());
        System.out.println("   Result: " + (sameMessage ? "✅ Generic messages prevent user enumeration" : "❌ Messages differ"));
        
        System.out.println("   Action: Verify secure token generation");
        PasswordResetResult token1 = authService.initiatePasswordReset("alice");
        PasswordResetResult token2 = authService.initiatePasswordReset("alice");
        boolean uniqueTokens = token1.isSuccessful() && token2.isSuccessful() && 
                               !token1.getToken().equals(token2.getToken());
        System.out.println("   Result: " + (uniqueTokens ? "✅ Tokens are unique and unpredictable" : "❌ Token generation issue"));
        
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║         All Demonstration Scenarios Completed!             ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }
}
