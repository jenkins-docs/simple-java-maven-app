package com.mycompany.app.web;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Home page servlet with authentication UI
 */
public class HomeServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        
        String sessionId = (String) req.getSession().getAttribute("sessionId");
        String username = (String) req.getSession().getAttribute("username");
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("    <title>Authentication System Demo</title>");
        out.println("    <style>");
        out.println("        * { margin: 0; padding: 0; box-sizing: border-box; }");
        out.println("        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; padding: 20px; }");
        out.println("        .container { max-width: 800px; margin: 0 auto; }");
        out.println("        .card { background: white; border-radius: 10px; padding: 30px; margin-bottom: 20px; box-shadow: 0 10px 30px rgba(0,0,0,0.2); }");
        out.println("        h1 { color: #333; margin-bottom: 10px; }");
        out.println("        h2 { color: #555; margin-bottom: 20px; font-size: 1.3em; }");
        out.println("        .status { padding: 15px; border-radius: 5px; margin-bottom: 20px; font-weight: bold; }");
        out.println("        .status.logged-in { background: #d4edda; color: #155724; border: 1px solid #c3e6cb; }");
        out.println("        .status.logged-out { background: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }");
        out.println("        .form-group { margin-bottom: 15px; }");
        out.println("        label { display: block; margin-bottom: 5px; color: #555; font-weight: 500; }");
        out.println("        input[type='text'], input[type='password'] { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 5px; font-size: 14px; }");
        out.println("        input[type='text']:focus, input[type='password']:focus { outline: none; border-color: #667eea; }");
        out.println("        button { background: #667eea; color: white; border: none; padding: 12px 24px; border-radius: 5px; cursor: pointer; font-size: 14px; font-weight: 500; margin-right: 10px; }");
        out.println("        button:hover { background: #5568d3; }");
        out.println("        button.secondary { background: #6c757d; }");
        out.println("        button.secondary:hover { background: #5a6268; }");
        out.println("        button.danger { background: #dc3545; }");
        out.println("        button.danger:hover { background: #c82333; }");
        out.println("        .credentials { background: #e7f3ff; padding: 15px; border-radius: 5px; margin-bottom: 20px; }");
        out.println("        .credentials h3 { color: #004085; margin-bottom: 10px; font-size: 1.1em; }");
        out.println("        .credentials ul { list-style: none; }");
        out.println("        .credentials li { padding: 5px 0; color: #004085; }");
        out.println("        .section { margin-top: 30px; padding-top: 30px; border-top: 2px solid #eee; }");
        out.println("        .message { padding: 10px; border-radius: 5px; margin-bottom: 15px; }");
        out.println("        .message.success { background: #d4edda; color: #155724; }");
        out.println("        .message.error { background: #f8d7da; color: #721c24; }");
        out.println("    </style>");
        out.println("</head>");
        out.println("<body>");
        out.println("    <div class='container'>");
        out.println("        <div class='card'>");
        out.println("            <h1>🔐 Authentication System Demo</h1>");
        out.println("            <p style='color: #666; margin-bottom: 20px;'>Test the complete authentication system in your browser</p>");
        
        // Status display
        if (sessionId != null && username != null) {
            out.println("            <div class='status logged-in'>");
            out.println("                ✅ Logged in as: <strong>" + username + "</strong>");
            out.println("                <br>Session ID: " + sessionId);
            out.println("            </div>");
        } else {
            out.println("            <div class='status logged-out'>");
            out.println("                🔴 Not logged in");
            out.println("            </div>");
        }
        
        // Sample credentials
        out.println("            <div class='credentials'>");
        out.println("                <h3>📋 Sample Credentials</h3>");
        out.println("                <ul>");
        out.println("                    <li>• Username: <strong>alice</strong> | Password: <strong>password123</strong></li>");
        out.println("                    <li>• Username: <strong>bob</strong> | Password: <strong>securePass456</strong></li>");
        out.println("                    <li>• Username: <strong>charlie</strong> | Password: <strong>myPassword789</strong></li>");
        out.println("                </ul>");
        out.println("            </div>");
        
        if (sessionId == null) {
            // Login form
            out.println("            <h2>Login</h2>");
            out.println("            <form action='/login' method='post'>");
            out.println("                <div class='form-group'>");
            out.println("                    <label for='username'>Username:</label>");
            out.println("                    <input type='text' id='username' name='username' required>");
            out.println("                </div>");
            out.println("                <div class='form-group'>");
            out.println("                    <label for='password'>Password:</label>");
            out.println("                    <input type='password' id='password' name='password' required>");
            out.println("                </div>");
            out.println("                <button type='submit'>Login</button>");
            out.println("            </form>");
            
            // Password reset section
            out.println("            <div class='section'>");
            out.println("                <h2>Password Reset</h2>");
            out.println("                <form action='/reset' method='post'>");
            out.println("                    <input type='hidden' name='action' value='initiate'>");
            out.println("                    <div class='form-group'>");
            out.println("                        <label for='reset-username'>Username:</label>");
            out.println("                        <input type='text' id='reset-username' name='username' required>");
            out.println("                    </div>");
            out.println("                    <button type='submit' class='secondary'>Request Reset Token</button>");
            out.println("                </form>");
            out.println("            </div>");
            
            // Complete reset section
            out.println("            <div class='section'>");
            out.println("                <h2>Complete Password Reset</h2>");
            out.println("                <form action='/reset' method='post'>");
            out.println("                    <input type='hidden' name='action' value='complete'>");
            out.println("                    <div class='form-group'>");
            out.println("                        <label for='token'>Reset Token:</label>");
            out.println("                        <input type='text' id='token' name='token' required>");
            out.println("                    </div>");
            out.println("                    <div class='form-group'>");
            out.println("                        <label for='new-password'>New Password:</label>");
            out.println("                        <input type='password' id='new-password' name='newPassword' required>");
            out.println("                    </div>");
            out.println("                    <button type='submit' class='secondary'>Reset Password</button>");
            out.println("                </form>");
            out.println("            </div>");
        } else {
            // Logged in actions
            out.println("            <h2>Actions</h2>");
            out.println("            <form action='/logout' method='post' style='display: inline;'>");
            out.println("                <button type='submit' class='danger'>Logout</button>");
            out.println("            </form>");
            out.println("            <form action='/status' method='get' style='display: inline;'>");
            out.println("                <button type='submit' class='secondary'>Check Status</button>");
            out.println("            </form>");
        }
        
        out.println("        </div>");
        out.println("    </div>");
        out.println("</body>");
        out.println("</html>");
    }
}
