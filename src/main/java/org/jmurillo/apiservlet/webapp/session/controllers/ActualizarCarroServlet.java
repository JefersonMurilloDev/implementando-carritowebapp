package org.jmurillo.apiservlet.webapp.session.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.jmurillo.apiservlet.webapp.session.models.Carro;

import java.io.IOException;
import java.util.Arrays;

@WebServlet("/carro/actualizar")
public class ActualizarCarroServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Carro carro = (Carro) session.getAttribute("carro");
        
        if (carro != null) {
            String accion = req.getParameter("accion");
            
            if ("actualizar".equals(accion)) {
                actualizarCantidades(req, carro);
            } else if ("eliminar".equals(accion)) {
                eliminarItems(req, carro);
            }
            
            // Actualizar el carro en la sesión
            session.setAttribute("carro", carro);
        }
        
        // Redireccionar de vuelta al carro
        resp.sendRedirect(req.getContextPath() + "/carro");
    }
    
    private void actualizarCantidades(HttpServletRequest req, Carro carro) {
        // Obtener todos los parámetros de cantidad
        String[] cantidades = req.getParameterValues("cantidad");
        String[] productosIds = req.getParameterValues("productoId");
        
        if (cantidades != null && productosIds != null) {
            for (int i = 0; i < cantidades.length && i < productosIds.length; i++) {
                try {
                    Long productoId = Long.parseLong(productosIds[i]);
                    int nuevaCantidad = Integer.parseInt(cantidades[i]);
                    carro.actualizarCantidad(productoId, nuevaCantidad);
                } catch (NumberFormatException e) {
                    // Ignorar valores inválidos
                }
            }
        }
    }
    
    private void eliminarItems(HttpServletRequest req, Carro carro) {
        // Obtener los IDs de productos seleccionados para eliminar
        String[] idsSeleccionados = req.getParameterValues("eliminar");
        
        if (idsSeleccionados != null) {
            Long[] productosIds = Arrays.stream(idsSeleccionados)
                    .map(Long::parseLong)
                    .toArray(Long[]::new);
            carro.eliminarItems(productosIds);
        }
    }
}