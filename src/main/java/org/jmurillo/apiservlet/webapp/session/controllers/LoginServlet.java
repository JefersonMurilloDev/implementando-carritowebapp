package org.jmurillo.apiservlet.webapp.session.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.jmurillo.apiservlet.webapp.session.services.LoginService;
import org.jmurillo.apiservlet.webapp.session.services.LoginServiceCookieImpl;
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
            try (PrintWriter uot = resp.getWriter()) {

                uot.println("<!DOCTYPE html");
                uot.println("<html>");
                uot.println("<head>");
                uot.println("        <meta charset=\"UTF-8\">");
                uot.println("        <title>Login Correcto</title>");
                uot.println("</head>");
                uot.println("   <body>");
                uot.println("       <h1>Hola " + usernameOptional.get() + " Has iniciado sesión con éxito!</h1>");
                uot.println("       <p>Para salir, pulse <a href='" + req.getContextPath() + "/index.html'>aquí</a>.</p>");
                uot.println("       <p>Para cerrar sesión, pulse <a href='" + req.getContextPath() + "/logout'>aquí</a>.</p>");
                uot.println("   </body>");
                uot.println("</html>");
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
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Lo sentimos no esta autorizado para acceder.");
        }
    }
}
