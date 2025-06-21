package org.jmurillo.apiservlet.webapp.session.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.jmurillo.apiservlet.webapp.session.models.Carro;
import org.jmurillo.apiservlet.webapp.session.models.ItemCarro;
import org.jmurillo.apiservlet.webapp.session.models.Producto;
import org.jmurillo.apiservlet.webapp.session.services.ProductoService;
import org.jmurillo.apiservlet.webapp.session.services.ProductoServiceImp;
import org.jmurillo.apiservlet.webapp.session.services.ProductoServiceJdbcImpl;

import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

@WebServlet("/carro/agregar")
public class AgregarCarroServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        Connection conn = (Connection) req.getAttribute("conn");
        ProductoService service = new ProductoServiceJdbcImpl(conn);
        Optional<Producto> producto = service.buscarProductoPorId(id);
        if (producto.isPresent()) {
            ItemCarro item = new ItemCarro(1, producto.get());
            HttpSession session = req.getSession();
            Carro carro = (Carro) session.getAttribute("carro");
            carro.addItemCarro(item);

        }
        resp.sendRedirect(req.getContextPath() + "/carro/ver");
        /*service.buscarProductoPorId(id).ifPresent(p -> req.getSession().setAttribute("producto", p));
        resp.sendRedirect(req.getContextPath() + "/productos");*/
    }
}
