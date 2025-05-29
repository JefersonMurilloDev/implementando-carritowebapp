
# 🛒 Tienda Online - Sistema de Carrito de Compras

Una aplicación web desarrollada con Java Servlets que implementa un sistema completo de tienda online con funcionalidades de carrito de compras, autenticación de usuarios y gestión de productos.

## 🚀 Características

- ✅ **Catálogo de Productos**: Visualización organizada de productos disponibles
- 🛍️ **Carrito de Compras**: Agregar, actualizar y eliminar productos del carrito
- 🔐 **Sistema de Autenticación**: Login y logout de usuarios
- 📱 **Diseño Responsive**: Interfaz adaptable a diferentes dispositivos
- 💾 **Gestión de Sesiones**: Mantenimiento del estado del usuario y carrito
- 🎨 **Interfaz Moderna**: Diseño atractivo con animaciones y efectos visuales

## 🛠️ Tecnologías Utilizadas

- **Backend**: Java (JDK 16)
- **Web Framework**: Java Servlets & JSP
- **Frontend**: HTML5, CSS3, JavaScript
- **Build Tool**: Maven
- **Servidor**: Apache Tomcat
- **IDE**: IntelliJ IDEA


## 🏃‍♂️ Inicio Rápido

### Prerequisitos

- Java JDK 16 o superior
- Apache Maven 3.6+
- Apache Tomcat 9.0+
- IntelliJ IDEA (recomendado)

### Instalación

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/tu-usuario/webapp-session-carro-compras.git
   cd webapp-session-carro-compras
   ```

2. **Compilar el proyecto**
   ```bash
   mvn clean compile
   ```

3. **Empaquetar la aplicación**
   ```bash
   mvn package
   ```

4. **Desplegar en Tomcat**
   - Copiar el archivo WAR generado en `target/` al directorio `webapps/` de Tomcat
   - O usar el plugin de Maven para Tomcat:
   ```bash
   mvn tomcat7:deploy
   ```

5. **Acceder a la aplicación**
   ```
   http://localhost:8080/webapp-session
   ```

## 📋 Funcionalidades Detalladas

### 🏠 Página Principal
- Interfaz de bienvenida con navegación intuitiva
- Diseño moderno con gradientes y animaciones
- Enlaces rápidos a todas las funcionalidades

### 📦 Gestión de Productos
- **ProductoServlet**: Maneja la visualización del catálogo
- **ProductoService**: Lógica de negocio para productos
- Listado organizado de productos con información detallada

### 🛍️ Sistema de Carrito
- **AgregarCarroServlet**: Añadir productos al carrito
- **ActualizarCarroServlet**: Modificar cantidades
- **VerCarroServlet**: Visualizar contenido del carrito
- **Modelo Carro**: Gestión del estado del carrito
- **ItemCarro**: Representación de elementos individuales

### 🔐 Autenticación
- **LoginServlet**: Proceso de inicio de sesión
- **LogoutServlet**: Cierre de sesión seguro
- **LoginService**: Interfaces para diferentes implementaciones
- Soporte para sesiones y cookies

## 🎨 Características de la Interfaz

- **Diseño Responsive**: Adaptable a móviles y tablets
- **Animaciones CSS**: Transiciones suaves y efectos hover
- **Gradientes Modernos**: Paleta de colores atractiva
- **Iconografía**: Uso de emojis para mejor UX
- **Cards Interactivas**: Elementos con feedback visual

## 🔧 Configuración de Desarrollo

### Maven Dependencies
El proyecto utiliza las siguientes dependencias principales:
- Java Servlet API
- JSP API
- JSTL (JavaServer Pages Standard Tag Library)


## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📝 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 👨‍💻 Autor

**J. Murillo**
- GitHub: [@JefersonMurilloDev](https://github.com/JefersonMurilloDev)
- Email: murillopalacioj@gmail.com

## 🙏 Agradecimientos

- Comunidad Java por la documentación y soporte
- Apache Foundation por Tomcat y Maven
- Contribuidores del proyecto

---

⭐ Si este proyecto te ha sido útil, ¡no olvides darle una estrella!

