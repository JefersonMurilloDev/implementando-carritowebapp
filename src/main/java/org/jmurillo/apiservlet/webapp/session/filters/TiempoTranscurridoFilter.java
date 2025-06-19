package org.jmurillo.apiservlet.webapp.session.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

/**
 * Filtro que mide el tiempo transcurrido en el procesamiento de cada petición HTTP.
 * Calcula la diferencia entre el tiempo de inicio y fin de la petición en milisegundos.
 */
@WebFilter("/*") // Aplica a todas las URLs de la aplicación
public class TiempoTranscurridoFilter implements Filter {

    /**
     * Método principal del filtro que se ejecuta en cada petición.
     * Implementa la lógica de "antes" y "después" para medir el tiempo.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        // === EVENTO "ANTES" ===
        // 1. Capturamos el tiempo de inicio en milisegundos
        long tiempoInicio = System.currentTimeMillis();
        
        // 2. Obtenemos información adicional para el log
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String metodo = httpRequest.getMethod();
        String url = httpRequest.getRequestURL().toString();
        String queryString = httpRequest.getQueryString();
        
        // Construir URL completa si hay parámetros
        String urlCompleta = url;
        if (queryString != null) {
            urlCompleta += "?" + queryString;
        }
        
        System.out.println("=== INICIANDO PETICIÓN ===");
        System.out.println("Método: " + metodo);
        System.out.println("URL: " + urlCompleta);
        System.out.println("Tiempo de inicio: " + tiempoInicio + " ms");
        
        try {
            // === PROCESAMIENTO ===
            // 3. Permitir que la petición continúe hacia el servlet
            // Aquí es donde se procesa realmente la petición
            chain.doFilter(request, response);
            
        } finally {
            // === EVENTO "DESPUÉS" ===
            // 4. Capturamos el tiempo de fin (se ejecuta SIEMPRE, incluso si hay errores)
            long tiempoFin = System.currentTimeMillis();
            
            // 5. Calculamos el tiempo transcurrido
            long tiempoTranscurrido = tiempoFin - tiempoInicio;
            
            // 6. Mostramos el resultado en consola
            System.out.println("=== PETICIÓN COMPLETADA ===");
            System.out.println("Tiempo de fin: " + tiempoFin + " ms");
            System.out.println("El tiempo de carga de la página es de " + tiempoTranscurrido + " milisegundos");
            System.out.println("=====================================\n");
        }
    }
    
    /**
     * Se ejecuta cuando se inicializa el filtro.
     * Útil para configuraciones iniciales.
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("TiempoTranscurridoFilter inicializado correctamente");
    }
    
    /**
     * Se ejecuta cuando se destruye el filtro.
     * Útil para limpieza de recursos.
     */
    @Override
    public void destroy() {
        System.out.println("TiempoTranscurridoFilter destruido");
    }
}