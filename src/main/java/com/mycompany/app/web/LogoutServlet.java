package com.mycompany.app.web;

import com.mycompany.app.auth.service.AuthenticationService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Logout servlet handling session termination
 */
public class LogoutServlet extends HttpServlet {
    
    private final AuthenticationService authService;
    
    public LogoutServlet(AuthenticationService authService) {
        this.authService = authService;
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        
        if (session != null) {
            String sessionId = (String) session.getAttribute("sessionId");
            if (sessionId != null) {
                authService.logout(sessionId);
            }
            session.invalidate();
        }
        
        resp.sendRedirect("/?message=logout_success");
    }
}
