package org.jmurillo.apiservlet.webapp.session.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.jmurillo.apiservlet.webapp.session.services.ServiceJdbcException;
import org.jmurillo.apiservlet.webapp.session.util.ConexionBaseDatos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Filtro de servlet que gestiona las conexiones a la base de datos para todas las peticiones.
 * <p>
 * Este filtro intercepta todas las peticiones HTTP (mediante el patrón "/*") y proporciona
 * una conexión a la base de datos que está disponible durante todo el ciclo de vida de la petición.
 * Además, maneja automáticamente las transacciones con commit y rollback según sea necesario.
 * </p>
 * 
 * <p>
 * <strong>Funcionalidades principales:</strong>
 * </p>
 * <ul>
 *   <li>Crea una conexión a la base de datos al inicio de cada petición</li>
 *   <li>Configura la conexión en modo transaccional (autoCommit = false)</li>
 *   <li>Pone la conexión disponible como atributo de la petición</li>
 *   <li>Realiza commit automático si la petición se procesa exitosamente</li>
 *   <li>Realiza rollback automático si ocurre algún error SQL</li>
 *   <li>Cierra automáticamente la conexión al finalizar</li>
 * </ul>
 * 
 * @author Tu nombre
 * @version 1.0
 * @since 1.0
 */
@WebFilter("/*")
public class ConexionFilter implements Filter {
    
    /**
     * Intercepta todas las peticiones HTTP y gestiona la conexión a la base de datos.
     * <p>
     * Este método se ejecuta antes de que la petición llegue al servlet destino y:
     * </p>
     * <ol>
     *   <li>Obtiene una conexión a la base de datos</li>
     *   <li>Configura la conexión en modo transaccional</li>
     *   <li>Almacena la conexión como atributo de la petición</li>
     *   <li>Permite que la petición continúe su flujo</li>
     *   <li>Realiza commit si todo va bien, o rollback si hay errores</li>
     * </ol>
     * 
     * @param request  la petición del servlet que contiene los datos enviados por el cliente
     * @param response la respuesta del servlet que contiene los datos que se enviarán al cliente
     * @param chain    el objeto FilterChain usado para invocar el siguiente filtro en la cadena
     * 
     * @throws IOException      si ocurre un error de entrada/salida durante el procesamiento
     * @throws ServletException si ocurre un error específico del servlet durante el procesamiento
     * 
     * @see ConexionBaseDatos#getConnection()
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {

        // Try-with-resources garantiza que la conexión se cierre automáticamente
        try (Connection conn = ConexionBaseDatos.getConnection()) {

            // Configurar la conexión en modo transaccional si no lo está ya
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }

            try {
                // Hacer la conexión disponible para servlets y otros filtros
                request.setAttribute("conn", conn);
                
                // Continuar con la cadena de filtros/servlets
                chain.doFilter(request, response);

                // Si llegamos aquí, todo fue exitoso - confirmar la transacción
                conn.commit();
                
            } catch (SQLException | ServiceJdbcException e) {
                // Error SQL - deshacer todos los cambios
                conn.rollback();
                
                // Enviar error HTTP 500 al cliente
                ((HttpServletResponse) response).sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                    e.getMessage()
                );
                
                // Log del error para debugging
                e.printStackTrace();
            }
            
        } catch (SQLException throwables) {
            // Error al obtener la conexión inicial
            throwables.printStackTrace();
        }
    }
}