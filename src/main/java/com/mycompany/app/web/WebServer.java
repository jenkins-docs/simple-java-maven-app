package com.mycompany.app.web;

import com.mycompany.app.auth.model.User;
import com.mycompany.app.auth.repository.InMemoryPasswordResetTokenRepository;
import com.mycompany.app.auth.repository.InMemorySessionRepository;
import com.mycompany.app.auth.repository.InMemoryUserRepository;
import com.mycompany.app.auth.service.AuthenticationService;
import com.mycompany.app.auth.service.AuthenticationServiceImpl;
import com.mycompany.app.auth.service.PasswordHasher;
import com.mycompany.app.auth.service.SecureIdentifierGenerator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.time.LocalDateTime;

/**
 * Embedded web server for browser-based authentication testing
 */
public class WebServer {
    
    private static final int PORT = 8080;
    private final Server server;
    private final AuthenticationService authService;
    
    public WebServer() {
        this.server = new Server(PORT);
        this.authService = initializeAuthService();
        setupServlets();
    }
    
    private AuthenticationService initializeAuthService() {
        // Initialize repositories
        InMemoryUserRepository userRepository = new InMemoryUserRepository();
        InMemorySessionRepository sessionRepository = new InMemorySessionRepository();
        InMemoryPasswordResetTokenRepository tokenRepository = new InMemoryPasswordResetTokenRepository();
        
        // Initialize security components
        PasswordHasher passwordHasher = new PasswordHasher();
        SecureIdentifierGenerator idGenerator = new SecureIdentifierGenerator();
        
        // Create sample users
        User alice = new User("alice", passwordHasher.hashPassword("password123"), LocalDateTime.now(), null);
        User bob = new User("bob", passwordHasher.hashPassword("securePass456"), LocalDateTime.now(), null);
        User charlie = new User("charlie", passwordHasher.hashPassword("myPassword789"), LocalDateTime.now(), null);
        
        userRepository.save(alice);
        userRepository.save(bob);
        userRepository.save(charlie);
        
        return new AuthenticationServiceImpl(
            userRepository,
            sessionRepository,
            tokenRepository,
            passwordHasher,
            idGenerator
        );
    }
    
    private void setupServlets() {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        
        // Add servlets
        context.addServlet(new ServletHolder(new HomeServlet()), "/");
        context.addServlet(new ServletHolder(new LoginServlet(authService)), "/login");
        context.addServlet(new ServletHolder(new LogoutServlet(authService)), "/logout");
        context.addServlet(new ServletHolder(new StatusServlet(authService)), "/status");
        context.addServlet(new ServletHolder(new ResetServlet(authService)), "/reset");
    }
    
    public void start() throws Exception {
        server.start();
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║   Authentication System Web Server Started!               ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("🌐 Open your browser and navigate to:");
        System.out.println("   http://localhost:" + PORT);
        System.out.println();
        System.out.println("📋 Sample Credentials:");
        System.out.println("   • Username: alice    | Password: password123");
        System.out.println("   • Username: bob      | Password: securePass456");
        System.out.println("   • Username: charlie  | Password: myPassword789");
        System.out.println();
        System.out.println("Press Ctrl+C to stop the server");
        System.out.println("=".repeat(60));
        server.join();
    }
    
    public void stop() throws Exception {
        server.stop();
    }
    
    public static void main(String[] args) {
        try {
            WebServer webServer = new WebServer();
            webServer.start();
        } catch (Exception e) {
            System.err.println("Failed to start web server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
