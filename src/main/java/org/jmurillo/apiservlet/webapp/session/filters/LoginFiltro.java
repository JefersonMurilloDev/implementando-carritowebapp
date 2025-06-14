package org.jmurillo.apiservlet.webapp.session.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jmurillo.apiservlet.webapp.session.services.LoginService;
import org.jmurillo.apiservlet.webapp.session.services.LoginServiceSessionImpl;

import java.io.IOException;
import java.util.Optional;

/**
 * Filtro de seguridad que intercepta las peticiones a las rutas protegidas del carro de compras.
 * Su propósito es verificar si el usuario ha iniciado sesión antes de permitir el acceso
 * a cualquier funcionalidad relacionada con el carro (ver, agregar, actualizar, etc.).
 */
@WebFilter({"/carro/*"})
public class LoginFiltro implements Filter {

    /**
     * Ejecuta la lógica del filtro para cada petición interceptada.
     * Comprueba la existencia de un nombre de usuario en la sesión HTTP.
     *
     * @param request  el objeto ServletRequest que contiene la petición del cliente.
     * @param response el objeto ServletResponse que contendrá la respuesta del filtro.
     * @param chain    un objeto FilterChain que permite invocar al siguiente filtro o recurso en la cadena.
     * @throws IOException      si ocurre un error de entrada/salida durante el procesamiento.
     * @throws ServletException si ocurre un error relacionado con el servlet.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LoginService service = new LoginServiceSessionImpl();
        Optional<String> username = service.getUsername((HttpServletRequest) request);

        // Si el Optional contiene un nombre de usuario, el usuario está autenticado.
        if (username.isPresent()) {
            // Permite que la petición continúe hacia el recurso solicitado (p. ej., el servlet del carro).
            chain.doFilter(request, response);
        } else {
            // Si el usuario no está autenticado, deniega el acceso.
            // Envía una respuesta de error HTTP 401 (No autorizado).
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Lo sentimos, no está autorizado para acceder a esta página.");
        }
    }
}