package org.jmurillo.apiservlet.webapp.session.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.jmurillo.apiservlet.webapp.session.services.LoginService;
import org.jmurillo.apiservlet.webapp.session.services.LoginServiceCookieImpl;
import org.jmurillo.apiservlet.webapp.session.services.LoginServiceSessionImpl;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginService auth = new LoginServiceSessionImpl();
        Optional<String> username = auth.getUsername(req);

        if (username.isPresent()) {
            HttpSession session = req.getSession();
            session.removeAttribute("username");
        }
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
