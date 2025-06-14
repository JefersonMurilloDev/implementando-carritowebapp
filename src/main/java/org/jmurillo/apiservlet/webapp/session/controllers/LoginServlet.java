package org.jmurillo.apiservlet.webapp.session.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.jmurillo.apiservlet.webapp.session.services.LoginService;
import org.jmurillo.apiservlet.webapp.session.services.LoginServiceSessionImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet({"/login", "/login.html"  })
public class LoginServlet extends HttpServlet {
    final static String USERNAME = "admin@gmail.com";
    final static String PASSWORD = "12345";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginService loginService = new LoginServiceSessionImpl();
        Optional<String> usernameOptional = loginService.getUsername(req);

        if (usernameOptional.isPresent()) {
            resp.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = resp.getWriter()) {

                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("    <meta charset=\"UTF-8\">");
                out.println("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
                out.println("    <title>¡Bienvenido! - Tienda Online</title>");
                out.println("    <style>");
                out.println("        body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif; margin: 0; }");
                out.println("        .container { min-height: 100vh; display: flex; align-items: center; justify-content: center; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); padding: 20px; }");
                out.println("        .card { background: white; padding: 50px; border-radius: 20px; box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1); text-align: center; max-width: 550px; width: 100%; }");
                out.println("        .card h1 { font-size: 2.3em; margin-bottom: 15px; background: linear-gradient(135deg, #667eea, #764ba2); -webkit-background-clip: text; -webkit-text-fill-color: transparent; background-clip: text; font-weight: 700; }");
                out.println("        .card p { font-size: 1.1em; color: #666; margin-bottom: 35px; line-height: 1.6; }");
                out.println("        .button-group { display: flex; justify-content: center; gap: 20px; flex-wrap: wrap; }");
                out.println("        .btn { padding: 14px 30px; border-radius: 10px; text-decoration: none; font-weight: 600; font-size: 1em; transition: all 0.3s ease; display: inline-block; border: none; cursor: pointer; }");
                out.println("        .btn-primary { background: linear-gradient(135deg, #667eea, #764ba2); color: white; }");
                out.println("        .btn-secondary { background-color: #e9ecef; color: #343a40; }");
                out.println("        .btn:hover { transform: translateY(-3px); box-shadow: 0 10px 20px rgba(0, 0, 0, 0.15); }");
                out.println("    </style>");
                out.println("</head>");
                out.println("<body>");
                out.println("    <div class=\"container\">");
                out.println("        <div class=\"card\">");
                out.println("            <h1>¡Hola, " + usernameOptional.get() + "!</h1>");
                out.println("            <p>Has iniciado sesión con éxito. ¡Bienvenido a la tienda!</p>");
                out.println("            <div class=\"button-group\">");
                out.println("                <a href='" + req.getContextPath() + "/index.html' class='btn btn-primary'>Ir a la Tienda</a>");
                out.println("                <a href='" + req.getContextPath() + "/logout' class='btn btn-secondary'>Cerrar Sesión</a>");
                out.println("            </div>");
                out.println("        </div>");
                out.println("    </div>");
                out.println("</body>");
                out.println("</html>");
            }
        } else {
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username.equals(USERNAME) && password.equals(PASSWORD)) {
            HttpSession session = req.getSession();
            session.setAttribute("username", username);

            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            // Redirigir de vuelta al JSP de login con un parámetro de error
            resp.sendRedirect(req.getContextPath() + "/login.jsp?error=true");
        }
    }
}