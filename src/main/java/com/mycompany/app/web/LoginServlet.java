package com.mycompany.app.web;

import com.mycompany.app.auth.result.AuthenticationResult;
import com.mycompany.app.auth.service.AuthenticationService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Login servlet handling authentication requests
 */
public class LoginServlet extends HttpServlet {
    
    private final AuthenticationService authService;
    
    public LoginServlet(AuthenticationService authService) {
        this.authService = authService;
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        
        AuthenticationResult result = authService.login(username, password);
        
        if (result.isSuccessful()) {
            HttpSession session = req.getSession();
            session.setAttribute("sessionId", result.getSessionId());
            session.setAttribute("username", username);
            resp.sendRedirect("/?message=login_success");
        } else {
            resp.sendRedirect("/?error=" + result.getErrorMessage());
        }
    }
}
