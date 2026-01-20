package com.mycompany.app.web;

import com.mycompany.app.auth.result.PasswordResetResult;
import com.mycompany.app.auth.service.AuthenticationService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Password reset servlet
 */
public class ResetServlet extends HttpServlet {
    
    private final AuthenticationService authService;
    
    public ResetServlet(AuthenticationService authService) {
        this.authService = authService;
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");
        
        if ("initiate".equals(action)) {
            handleInitiateReset(req, resp);
        } else if ("complete".equals(action)) {
            handleCompleteReset(req, resp);
        } else {
            resp.sendRedirect("/?error=Invalid action");
        }
    }
    
    private void handleInitiateReset(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        PasswordResetResult result = authService.initiatePasswordReset(username);
        
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("    <title>Password Reset Token</title>");
        out.println("    <style>");
        out.println("        body { font-family: Arial, sans-serif; background: #f5f5f5; padding: 20px; }");
        out.println("        .card { background: white; border-radius: 10px; padding: 30px; max-width: 600px; margin: 0 auto; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }");
        out.println("        h1 { color: #333; }");
        out.println("        .token { background: #e7f3ff; padding: 15px; border-radius: 5px; margin: 20px 0; word-break: break-all; }");
        out.println("        .success { color: #155724; background: #d4edda; padding: 15px; border-radius: 5px; margin: 20px 0; }");
        out.println("        .error { color: #721c24; background: #f8d7da; padding: 15px; border-radius: 5px; margin: 20px 0; }");
        out.println("        a { display: inline-block; margin-top: 20px; color: #667eea; text-decoration: none; }");
        out.println("    </style>");
        out.println("</head>");
        out.println("<body>");
        out.println("    <div class='card'>");
        out.println("        <h1>Password Reset</h1>");
        
        if (result.isSuccessful()) {
            out.println("        <div class='success'>");
            out.println("            ✅ Password reset token generated successfully!");
            out.println("        </div>");
            out.println("        <p>Copy this token to complete the password reset:</p>");
            out.println("        <div class='token'>");
            out.println("            <strong>" + result.getToken() + "</strong>");
            out.println("        </div>");
            out.println("        <p><small>In a real system, this would be sent via email. The token expires in 1 hour.</small></p>");
        } else {
            out.println("        <div class='error'>");
            out.println("            ⚠️ " + result.getErrorMessage());
            out.println("        </div>");
        }
        
        out.println("        <a href='/'>← Back to Home</a>");
        out.println("    </div>");
        out.println("</body>");
        out.println("</html>");
    }
    
    private void handleCompleteReset(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String token = req.getParameter("token");
        String newPassword = req.getParameter("newPassword");
        
        boolean success = authService.completePasswordReset(token, newPassword);
        
        if (success) {
            resp.sendRedirect("/?message=password_reset_success");
        } else {
            resp.sendRedirect("/?error=Password reset failed. Token may be invalid or expired.");
        }
    }
}
