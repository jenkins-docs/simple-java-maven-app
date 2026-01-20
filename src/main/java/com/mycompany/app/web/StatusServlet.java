package com.mycompany.app.web;

import com.mycompany.app.auth.service.AuthenticationService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Status servlet for checking authentication status
 */
public class StatusServlet extends HttpServlet {
    
    private final AuthenticationService authService;
    
    public StatusServlet(AuthenticationService authService) {
        this.authService = authService;
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        
        HttpSession session = req.getSession(false);
        String sessionId = session != null ? (String) session.getAttribute("sessionId") : null;
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("    <title>Authentication Status</title>");
        out.println("    <style>");
        out.println("        body { font-family: Arial, sans-serif; background: #f5f5f5; padding: 20px; }");
        out.println("        .card { background: white; border-radius: 10px; padding: 30px; max-width: 600px; margin: 0 auto; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }");
        out.println("        h1 { color: #333; }");
        out.println("        .status { padding: 15px; border-radius: 5px; margin: 20px 0; }");
        out.println("        .authenticated { background: #d4edda; color: #155724; }");
        out.println("        .not-authenticated { background: #f8d7da; color: #721c24; }");
        out.println("        a { display: inline-block; margin-top: 20px; color: #667eea; text-decoration: none; }");
        out.println("    </style>");
        out.println("</head>");
        out.println("<body>");
        out.println("    <div class='card'>");
        out.println("        <h1>Authentication Status</h1>");
        
        if (sessionId != null && authService.isAuthenticated(sessionId)) {
            String username = authService.getCurrentUser(sessionId);
            out.println("        <div class='status authenticated'>");
            out.println("            <strong>✅ Authenticated</strong><br>");
            out.println("            Username: " + username + "<br>");
            out.println("            Session ID: " + sessionId);
            out.println("        </div>");
        } else {
            out.println("        <div class='status not-authenticated'>");
            out.println("            <strong>❌ Not Authenticated</strong><br>");
            out.println("            No valid session found");
            out.println("        </div>");
        }
        
        out.println("        <a href='/'>← Back to Home</a>");
        out.println("    </div>");
        out.println("</body>");
        out.println("</html>");
    }
}
