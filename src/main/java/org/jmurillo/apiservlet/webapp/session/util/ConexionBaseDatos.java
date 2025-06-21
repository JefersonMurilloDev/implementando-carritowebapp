package org.jmurillo.apiservlet.webapp.session.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase utilitaria para la gestión de conexiones a la base de datos MySQL.
 * <p>
 * Esta clase ha sido actualizada para obtener configuración desde archivos externos
 * y variables de entorno, mejorando la seguridad y flexibilidad del sistema.
 * </p>
 * 
 * <p>
 * <strong>Fuentes de configuración (en orden de prioridad):</strong>
 * </p>
 * <ol>
 *   <li><strong>config.properties:</strong> Archivo local con configuración específica</li>
 *   <li><strong>Variables de entorno:</strong> DB_URL, DB_USER, DB_PASSWORD</li>
 *   <li><strong>Valores por defecto:</strong> Para desarrollo local</li>
 * </ol>
 * 
 * <p>
 * <strong>Configuración esperada en config.properties:</strong>
 * </p>
 * <pre>
 * db.url=jdbc:mysql://localhost:3306/java_curso?serverTimeZone=America/Bogota
 * db.user=root
 * db.password=tu_password
 * environment=development
 * </pre>
 * 
 * <p>
 * <strong>Mejoras implementadas:</strong>
 * </p>
 * <ul>
 *   <li>✅ Configuración externa (no hardcodeada)</li>
 *   <li>✅ Múltiples fuentes de configuración</li>
 *   <li>✅ Validación de entornos de producción</li>
 *   <li>✅ Método de prueba de conexión</li>
 *   <li>✅ Información de configuración para debugging</li>
 * </ul>
 * 
 * @author Tu nombre
 * @version 2.0 - Actualizada para usar configuración externa
 * @since 1.0
 * 
 * @see ConfigLoader
 * @see java.sql.Connection
 * @see java.sql.DriverManager
 */
public class ConexionBaseDatos {

    // ========== VALORES POR DEFECTO PARA DESARROLLO ==========
    
    /**
     * URL por defecto para desarrollo local.
     * Solo se usa si no se encuentra configuración en otros lugares.
     */
    private static final String DEFAULT_URL = 
        "jdbc:mysql://localhost:3306/java_curso?serverTimeZone=America/Bogotá";
    
    /**
     * Usuario por defecto para desarrollo local.
     */
    private static final String DEFAULT_USER = "root";
    
    /**
     * Contraseña por defecto para desarrollo local.
     * ⚠️ NUNCA debe usarse en producción.
     */
    private static final String DEFAULT_PASSWORD = "password";

    // ========== MÉTODOS PARA OBTENER CONFIGURACIÓN ==========

    /**
     * Obtiene la URL de conexión a la base de datos.
     * <p>
     * <strong>Orden de búsqueda:</strong>
     * </p>
     * <ol>
     *   <li>config.properties → db.url</li>
     *   <li>Variable de entorno → DB_URL</li>
     *   <li>Valor por defecto para desarrollo</li>
     * </ol>
     * 
     * @return URL de conexión a la base de datos
     */
    private static String getDatabaseUrl() {
        return ConfigLoader.getProperty("db.url", DEFAULT_URL);
    }

    /**
     * Obtiene el nombre de usuario para la base de datos.
     * <p>
     * <strong>Orden de búsqueda:</strong>
     * </p>
     * <ol>
     *   <li>config.properties → db.user</li>
     *   <li>Variable de entorno → DB_USER</li>
     *   <li>Valor por defecto</li>
     * </ol>
     * 
     * @return nombre de usuario para la base de datos
     */
    private static String getDatabaseUser() {
        return ConfigLoader.getProperty("db.user", DEFAULT_USER);
    }

    /**
     * Obtiene la contraseña para la base de datos.
     * <p>
     * <strong>Orden de búsqueda:</strong>
     * </p>
     * <ol>
     *   <li>config.properties → db.password</li>
     *   <li>Variable de entorno → DB_PASSWORD</li>
     *   <li>Valor por defecto (solo desarrollo)</li>
     * </ol>
     * 
     * @return contraseña para la base de datos
     */
    private static String getDatabasePassword() {
        return ConfigLoader.getProperty("db.password", DEFAULT_PASSWORD);
    }

    /**
     * Obtiene el entorno de ejecución actual.
     * 
     * @return el entorno (development, testing, production)
     */
    private static String getEnvironment() {
        return ConfigLoader.getProperty("environment", "development");
    }

    // ========== MÉTODO PRINCIPAL DE CONEXIÓN ==========

    /**
     * Obtiene una nueva conexión a la base de datos MySQL.
     * <p>
     * Este método es el punto de entrada principal para obtener conexiones.
     * Realiza validaciones de seguridad antes de crear la conexión.
     * </p>
     * 
     * <p>
     * <strong>Proceso de conexión:</strong>
     * </p>
     * <ol>
     *   <li>Obtiene configuración desde ConfigLoader</li>
     *   <li>Valida configuración para entorno de producción</li>
     *   <li>Crea conexión usando DriverManager</li>
     *   <li>Retorna conexión lista para usar</li>
     * </ol>
     * 
     * <p>
     * <strong>Características de la conexión:</strong>
     * </p>
     * <ul>
     *   <li>Nueva conexión en cada llamada</li>
     *   <li>AutoCommit habilitado por defecto</li>
     *   <li>Configuración de zona horaria incluida</li>
     *   <li>Validación de seguridad automática</li>
     * </ul>
     * 
     * @return nueva instancia de Connection conectada a MySQL
     * 
     * @throws SQLException si no se puede establecer la conexión por:
     *                     <ul>
     *                       <li>Servidor de base de datos no disponible</li>
     *                       <li>Credenciales incorrectas</li>
     *                       <li>Base de datos no existe</li>
     *                       <li>Driver MySQL no encontrado</li>
     *                       <li>Problemas de red/firewall</li>
     *                     </ul>
     * 
     * @throws IllegalStateException si la configuración no es válida para producción
     * 
     * @example
     * <pre>{@code
     * // Uso recomendado con try-with-resources
     * try (Connection conn = ConexionBaseDatos.getConnection()) {
     *     PreparedStatement stmt = conn.prepareStatement("SELECT * FROM usuarios WHERE id = ?");
     *     stmt.setInt(1, userId);
     *     ResultSet rs = stmt.executeQuery();
     *     
     *     while (rs.next()) {
     *         // Procesar resultados
     *         String nombre = rs.getString("nombre");
     *         System.out.println("Usuario: " + nombre);
     *     }
     * } catch (SQLException e) {
     *     System.err.println("Error de base de datos: " + e.getMessage());
     *     // Manejar error apropiadamente
     * }
     * }</pre>
     * 
     * @see ConfigLoader#getProperty(String, String)
     * @see #validateProductionConfiguration(String, String, String, String)
     */
    public static Connection getConnection() throws SQLException {
        // Obtener configuración
        String url = getDatabaseUrl();
        String user = getDatabaseUser();
        String password = getDatabasePassword();
        String environment = getEnvironment();
        
        // Validar configuración de producción
        validateProductionConfiguration(url, user, password, environment);
        
        // Crear y retornar conexión
        return DriverManager.getConnection(url, user, password);
    }

    // ========== MÉTODOS DE VALIDACIÓN Y UTILIDAD ==========

    /**
     * Valida que la configuración sea segura para entornos de producción.
     * <p>
     * En producción, este método verifica:
     * </p>
     * <ul>
     *   <li>No se use la contraseña por defecto</li>
     *   <li>No se use localhost como servidor</li>
     *   <li>No se use el usuario 'root' genérico</li>
     * </ul>
     * 
     * @param url URL de conexión
     * @param user nombre de usuario
     * @param password contraseña
     * @param environment entorno de ejecución
     * 
     * @throws IllegalStateException si la configuración no es segura para producción
     */
    private static void validateProductionConfiguration(String url, String user, 
                                                       String password, String environment) {
        if (!"production".equalsIgnoreCase(environment)) {
            return; // Solo validar en producción
        }
        
        // Validaciones de seguridad para producción
        if (DEFAULT_PASSWORD.equals(password)) {
            throw new IllegalStateException(
                "❌ SEGURIDAD: No se puede usar la contraseña por defecto en producción. " +
                "Configura db.password en config.properties o la variable de entorno DB_PASSWORD."
            );
        }
        
        if (url.contains("localhost") || url.contains("127.0.0.1")) {
            throw new IllegalStateException(
                "❌ SEGURIDAD: No se puede usar localhost en producción. " +
                "Configura un servidor de base de datos real en db.url."
            );
        }
        
        if ("root".equals(user)) {
            throw new IllegalStateException(
                "❌ SEGURIDAD: No se recomienda usar 'root' en producción. " +
                "Crea un usuario específico para la aplicación."
            );
        }
    }

    /**
     * Prueba la conexión a la base de datos sin lanzar excepciones.
     * <p>
     * Método utilitario para verificar que la configuración de conexión
     * es correcta y que la base de datos está disponible.
     * </p>
     * 
     * @return true si la conexión es exitosa, false en caso contrario
     * 
     * @example
     * <pre>{@code
     * if (ConexionBaseDatos.testConnection()) {
     *     System.out.println("✅ Base de datos disponible");
     * } else {
     *     System.out.println("❌ No se puede conectar a la base de datos");
     * }
     * }</pre>
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            // Verificar que la conexión esté activa
            return conn != null && !conn.isClosed() && conn.isValid(5);
        } catch (Exception e) {
            System.err.println("❌ Error probando conexión: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene información detallada sobre la configuración actual.
     * <p>
     * Útil para debugging y verificación de que la configuración
     * se está cargando correctamente. NO expone información sensible.
     * </p>
     * 
     * @return String formateado con información de configuración
     * 
     * @example
     * <pre>{@code
     * System.out.println(ConexionBaseDatos.getConfigurationInfo());
     * // Salida:
     * // === CONFIGURACIÓN DE BASE DE DATOS ===
     * // URL: jdbc:mysql://localhost:3306/java_curso?serverTimeZone=America/Bogota
     * // Usuario: root
     * // Contraseña: ***OCULTO***
     * // Entorno: development
     * // Estado: ✅ Configuración válida
     * }</pre>
     */
    public static String getConfigurationInfo() {
        StringBuilder info = new StringBuilder();
        info.append("=== CONFIGURACIÓN DE BASE DE DATOS ===\n");
        
        try {
            String url = getDatabaseUrl();
            String user = getDatabaseUser();
            String environment = getEnvironment();
            
            info.append(String.format("URL: %s\n", url));
            info.append(String.format("Usuario: %s\n", user));
            info.append("Contraseña: ***OCULTO***\n");
            info.append(String.format("Entorno: %s\n", environment));
            
            // Probar conexión
            if (testConnection()) {
                info.append("Estado: ✅ Conexión exitosa\n");
            } else {
                info.append("Estado: ❌ Error de conexión\n");
            }
            
        } catch (Exception e) {
            info.append(String.format("Estado: ❌ Error en configuración: %s\n", e.getMessage()));
        }
        
        info.append("\n");
        info.append(ConfigLoader.getConfigurationInfo());
        
        return info.toString();
    }

    /**
     * Obtiene información sobre la base de datos conectada.
     * <p>
     * Proporciona detalles técnicos sobre la conexión activa,
     * útil para verificación y debugging.
     * </p>
     * 
     * @return información de la base de datos o mensaje de error
     * 
     * @example
     * <pre>{@code
     * System.out.println(ConexionBaseDatos.getDatabaseInfo());
     * }</pre>
     */
    public static String getDatabaseInfo() {
        try (Connection conn = getConnection()) {
            var metadata = conn.getMetaData();
            
            return String.format(
                "=== INFORMACIÓN DE BASE DE DATOS ===\n" +
                "Producto: %s %s\n" +
                "Driver: %s %s\n" +
                "URL: %s\n" +
                "Usuario conectado: %s\n" +
                "Esquema actual: %s\n" +
                "Transacciones soportadas: %s\n" +
                "Auto-commit: %s",
                
                metadata.getDatabaseProductName(),
                metadata.getDatabaseProductVersion(),
                metadata.getDriverName(),
                metadata.getDriverVersion(),
                metadata.getURL(),
                metadata.getUserName(),
                conn.getSchema(),
                metadata.supportsTransactions() ? "Sí" : "No",
                conn.getAutoCommit() ? "Habilitado" : "Deshabilitado"
            );
            
        } catch (SQLException e) {
            return "❌ Error obteniendo información de base de datos: " + e.getMessage();
        }
    }
}