package org.jmurillo.apiservlet.webapp.session.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Clase utilitaria para cargar configuraciones desde archivos de propiedades.
 * <p>
 * Esta clase maneja la carga de configuraciones con el siguiente orden de prioridad:
 * </p>
 * <ol>
 *   <li>config.properties (archivo local con credenciales reales)</li>
 *   <li>Variables de entorno del sistema</li>
 *   <li>Valores por defecto hardcodeados</li>
 * </ol>
 * 
 * <p>
 * <strong>Patrón Singleton:</strong> Se inicializa solo una vez y reutiliza la configuración.
 * </p>
 * 
 * @author Tu nombre
 * @version 1.0
 * @since 2.0
 */
public class ConfigLoader {
    
    /** Properties object que contiene toda la configuración cargada */
    private static Properties properties;
    
    /** Flag para controlar si ya se inicializó la configuración */
    private static boolean initialized = false;
    
    /**
     * Inicializa la configuración cargando el archivo de propiedades.
     * <p>
     * Este método se ejecuta solo una vez (patrón Singleton lazy loading).
     * Intenta cargar config.properties desde el classpath (carpeta resources).
     * </p>
     * 
     * <p>
     * <strong>Flujo de inicialización:</strong>
     * </p>
     * <ol>
     *   <li>Verifica si ya está inicializado</li>
     *   <li>Crea un nuevo objeto Properties</li>
     *   <li>Intenta cargar config.properties desde resources</li>
     *   <li>Si no lo encuentra, usa valores por defecto</li>
     *   <li>Marca como inicializado</li>
     * </ol>
     */
    private static synchronized void initializeConfig() {
        if (initialized) {
            return; // Ya está inicializado, no hacer nada
        }
        
        properties = new Properties();
        
        // Intentar cargar config.properties desde el classpath
        InputStream configStream = ConfigLoader.class.getClassLoader()
            .getResourceAsStream("config.properties");
        
        if (configStream != null) {
            try {
                properties.load(configStream);
                System.out.println("✅ Configuración cargada exitosamente desde config.properties");
                configStream.close();
            } catch (IOException e) {
                System.err.println("❌ Error al cargar config.properties: " + e.getMessage());
                // Continuar con configuración por defecto
            }
        } else {
            System.err.println("⚠️ Archivo config.properties no encontrado, usando configuración por defecto");
            // El objeto properties queda vacío, se usarán los valores por defecto
        }
        
        initialized = true;
    }
    
    /**
     * Obtiene un valor de configuración con valor por defecto.
     * <p>
     * Busca la clave en el siguiente orden:
     * </p>
     * <ol>
     *   <li>En el archivo config.properties</li>
     *   <li>En las variables de entorno del sistema</li>
     *   <li>Retorna el valor por defecto proporcionado</li>
     * </ol>
     * 
     * @param key clave de la propiedad a buscar
     * @param defaultValue valor a retornar si no se encuentra la clave
     * @return el valor encontrado o el valor por defecto
     * 
     * @example
     * <pre>{@code
     * // Obtener URL de base de datos con fallback
     * String dbUrl = ConfigLoader.getProperty("db.url", "jdbc:mysql://localhost:3306/test");
     * }</pre>
     */
    public static String getProperty(String key, String defaultValue) {
        initializeConfig();
        
        // 1. Intentar obtener desde archivo properties
        String value = properties.getProperty(key);
        if (value != null && !value.trim().isEmpty()) {
            return value.trim();
        }
        
        // 2. Intentar obtener desde variables de entorno
        // Convertir db.url -> DB_URL, db.user -> DB_USER, etc.
        String envKey = key.toUpperCase().replace(".", "_");
        String envValue = System.getenv(envKey);
        if (envValue != null && !envValue.trim().isEmpty()) {
            return envValue.trim();
        }
        
        // 3. Retornar valor por defecto
        return defaultValue;
    }
    
    /**
     * Obtiene un valor de configuración obligatorio.
     * <p>
     * Similar a getProperty(), pero lanza una excepción si no encuentra el valor.
     * Útil para configuraciones críticas que son obligatorias.
     * </p>
     * 
     * @param key clave de la propiedad requerida
     * @return el valor de la propiedad
     * @throws IllegalStateException si la propiedad no se encuentra
     * 
     * @example
     * <pre>{@code
     * // Obtener contraseña obligatoria (lanza excepción si no existe)
     * String password = ConfigLoader.getRequiredProperty("db.password");
     * }</pre>
     */
    public static String getRequiredProperty(String key) {
        String value = getProperty(key, null);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException(
                String.format("❌ Propiedad requerida no encontrada: '%s'. " +
                             "Verifica config.properties o variables de entorno.", key)
            );
        }
        return value;
    }
    
    /**
     * Obtiene un valor numérico de configuración.
     * 
     * @param key clave de la propiedad
     * @param defaultValue valor por defecto si no se encuentra o no es un número válido
     * @return el valor numérico encontrado o el valor por defecto
     * 
     * @example
     * <pre>{@code
     * // Obtener tamaño del pool con valor por defecto
     * int poolSize = ConfigLoader.getIntProperty("db.pool.max.size", 10);
     * }</pre>
     */
    public static int getIntProperty(String key, int defaultValue) {
        String value = getProperty(key, String.valueOf(defaultValue));
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            System.err.println(String.format(
                "⚠️ Valor no numérico para '%s': '%s'. Usando valor por defecto: %d", 
                key, value, defaultValue
            ));
            return defaultValue;
        }
    }
    
    /**
     * Muestra información de configuración actual (ocultando datos sensibles).
     * <p>
     * Útil para debugging y verificación de que la configuración se cargó correctamente.
     * Automáticamente oculta valores que contengan palabras sensibles como "password".
     * </p>
     * 
     * @return String formateado con la información de configuración
     */
    public static String getConfigurationInfo() {
        initializeConfig();
        
        StringBuilder info = new StringBuilder();
        info.append("=== INFORMACIÓN DE CONFIGURACIÓN ===\n");
        info.append(String.format("Estado: %s\n", initialized ? "Inicializado" : "No inicializado"));
        info.append(String.format("Propiedades cargadas: %d\n", properties.size()));
        info.append("Valores:\n");
        
        // Mostrar todas las propiedades (ocultando las sensibles)
        for (String key : properties.stringPropertyNames()) {
            String value = properties.getProperty(key);
            
            // Ocultar información sensible
            if (isSensitiveKey(key)) {
                value = "***OCULTO***";
            }
            
            info.append(String.format("  %s = %s\n", key, value));
        }
        
        return info.toString();
    }
    
    /**
     * Determina si una clave contiene información sensible que debe ocultarse.
     * 
     * @param key la clave a evaluar
     * @return true si es sensible, false en caso contrario
     */
    private static boolean isSensitiveKey(String key) {
        String lowerKey = key.toLowerCase();
        return lowerKey.contains("password") || 
               lowerKey.contains("pass") ||
               lowerKey.contains("secret") ||
               lowerKey.contains("key") ||
               lowerKey.contains("token");
    }
}