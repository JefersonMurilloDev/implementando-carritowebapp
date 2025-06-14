package org.jmurillo.apiservlet.webapp.session.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jmurillo.apiservlet.webapp.session.models.Producto;
import org.jmurillo.apiservlet.webapp.session.services.LoginService;
import org.jmurillo.apiservlet.webapp.session.services.LoginServiceSessionImpl;
import org.jmurillo.apiservlet.webapp.session.services.ProductoService;
import org.jmurillo.apiservlet.webapp.session.services.ProductoServiceImp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@WebServlet({"/productos.html", "/productos"})
public class ProductoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductoService service = new ProductoServiceImp();
        List<Producto> productos = service.listar();

        LoginService loginService = new LoginServiceSessionImpl();
        Optional<String> usernameOptional = loginService.getUsername(req);

        String mensajeRequest = (String) req.getAttribute("mensaje");
        String mensajeApp = (String) getServletContext().getAttribute("mensaje");

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html lang='es'>");
            out.println("<head>");
            out.println("    <meta charset='UTF-8'>");
            out.println("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("    <title>Catálogo de Productos - Tienda Online</title>");
            out.println("    <link rel='stylesheet' href='" + req.getContextPath() + "/style.css'>");
            out.println("    <style>");
            out.println("        .productos-container {");
            out.println("            max-width: 1200px;");
            out.println("            margin: 20px auto;");
            out.println("            padding: 20px;");
            out.println("            background: rgba(255, 255, 255, 0.95);");
            out.println("            border-radius: 15px;");
            out.println("            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);");
            out.println("        }");
            out.println("        ");
            out.println("        .header-section {");
            out.println("            text-align: center;");
            out.println("            margin-bottom: 30px;");
            out.println("            padding-bottom: 20px;");
            out.println("            border-bottom: 2px solid #eee;");
            out.println("        }");
            out.println("        ");
            out.println("        .page-title {");
            out.println("            font-size: 2.5em;");
            out.println("            color: #333;");
            out.println("            margin-bottom: 15px;");
            out.println("            background: linear-gradient(135deg, #667eea, #764ba2);");
            out.println("            -webkit-background-clip: text;");
            out.println("            -webkit-text-fill-color: transparent;");
            out.println("            background-clip: text;");
            out.println("        }");
            out.println("        ");
            out.println("        .welcome-message {");
            out.println("            background: linear-gradient(135deg, #667eea, #764ba2);");
            out.println("            color: white;");
            out.println("            padding: 15px 25px;");
            out.println("            border-radius: 25px;");
            out.println("            display: inline-block;");
            out.println("            font-weight: 600;");
            out.println("            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.3);");
            out.println("            margin-bottom: 20px;");
            out.println("        }");
            out.println("        ");
            out.println("        .productos-grid {");
            out.println("            display: grid;");
            out.println("            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));");
            out.println("            gap: 25px;");
            out.println("            margin: 30px 0;");
            out.println("        }");
            out.println("        ");
            out.println("        .producto-card {");
            out.println("            background: white;");
            out.println("            border-radius: 15px;");
            out.println("            padding: 25px;");
            out.println("            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);");
            out.println("            transition: all 0.3s ease;");
            out.println("            border: 2px solid transparent;");
            out.println("            position: relative;");
            out.println("            overflow: hidden;");
            out.println("        }");
            out.println("        ");
            out.println("        .producto-card::before {");
            out.println("            content: '';");
            out.println("            position: absolute;");
            out.println("            top: 0;");
            out.println("            left: -100%;");
            out.println("            width: 100%;");
            out.println("            height: 100%;");
            out.println("            background: linear-gradient(90deg, transparent, rgba(102, 126, 234, 0.1), transparent);");
            out.println("            transition: left 0.5s ease;");
            out.println("        }");
            out.println("        ");
            out.println("        .producto-card:hover::before {");
            out.println("            left: 100%;");
            out.println("        }");
            out.println("        ");
            out.println("        .producto-card:hover {");
            out.println("            transform: translateY(-8px);");
            out.println("            box-shadow: 0 15px 30px rgba(102, 126, 234, 0.2);");
            out.println("            border-color: #667eea;");
            out.println("        }");
            out.println("        ");
            out.println("        .producto-id {");
            out.println("            background: #f8f9fa;");
            out.println("            color: #666;");
            out.println("            padding: 5px 12px;");
            out.println("            border-radius: 15px;");
            out.println("            font-size: 0.8em;");
            out.println("            font-weight: 600;");
            out.println("            position: absolute;");
            out.println("            top: 15px;");
            out.println("            right: 15px;");
            out.println("        }");
            out.println("        ");
            out.println("        .producto-nombre {");
            out.println("            font-size: 1.4em;");
            out.println("            font-weight: 700;");
            out.println("            color: #333;");
            out.println("            margin: 15px 0 10px 0;");
            out.println("            line-height: 1.3;");
            out.println("        }");
            out.println("        ");
            out.println("        .producto-tipo {");
            out.println("            background: linear-gradient(135deg, #FF6B6B, #EE5A52);");
            out.println("            color: white;");
            out.println("            padding: 8px 15px;");
            out.println("            border-radius: 20px;");
            out.println("            font-size: 0.9em;");
            out.println("            font-weight: 600;");
            out.println("            display: inline-block;");
            out.println("            margin-bottom: 15px;");
            out.println("        }");
            out.println("        ");
            out.println("        .producto-precio {");
            out.println("            font-size: 1.8em;");
            out.println("            font-weight: 700;");
            out.println("            color: #28a745;");
            out.println("            margin: 15px 0;");
            out.println("        }");
            out.println("        ");
            out.println("        .btn-agregar {");
            out.println("            width: 100%;");
            out.println("            padding: 12px 20px;");
            out.println("            background: linear-gradient(135deg, #28a745, #20c997);");
            out.println("            color: white;");
            out.println("            text-decoration: none;");
            out.println("            border-radius: 10px;");
            out.println("            font-weight: 600;");
            out.println("            text-align: center;");
            out.println("            display: block;");
            out.println("            transition: all 0.3s ease;");
            out.println("            margin-top: 15px;");
            out.println("        }");
            out.println("        ");
            out.println("        .btn-agregar:hover {");
            out.println("            transform: translateY(-2px);");
            out.println("            box-shadow: 0 5px 15px rgba(40, 167, 69, 0.4);");
            out.println("            color: white;");
            out.println("            text-decoration: none;");
            out.println("        }");
            out.println("        ");
            out.println("        .navigation-section {");
            out.println("            text-align: center;");
            out.println("            margin-top: 40px;");
            out.println("            padding-top: 30px;");
            out.println("            border-top: 2px solid #eee;");
            out.println("        }");
            out.println("        ");
            out.println("        .nav-btn {");
            out.println("            display: inline-block;");
            out.println("            padding: 12px 25px;");
            out.println("            margin: 0 10px;");
            out.println("            background: linear-gradient(135deg, #667eea, #764ba2);");
            out.println("            color: white;");
            out.println("            text-decoration: none;");
            out.println("            border-radius: 25px;");
            out.println("            font-weight: 600;");
            out.println("            transition: all 0.3s ease;");
            out.println("        }");
            out.println("        ");
            out.println("        .nav-btn:hover {");
            out.println("            transform: translateY(-2px);");
            out.println("            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);");
            out.println("            color: white;");
            out.println("            text-decoration: none;");
            out.println("        }");
            out.println("        ");
            out.println("        .guest-message {");
            out.println("            background: #fff3cd;");
            out.println("            color: #856404;");
            out.println("            padding: 15px 25px;");
            out.println("            border-radius: 10px;");
            out.println("            border: 1px solid #ffeaa7;");
            out.println("            margin-bottom: 20px;");
            out.println("            text-align: center;");
            out.println("        }");
            out.println("        ");
            out.println("        .producto-icon {");
            out.println("            font-size: 3em;");
            out.println("            margin-bottom: 15px;");
            out.println("            display: block;");
            out.println("        }");
            out.println("        ");
            out.println("        @keyframes fadeInUp {");
            out.println("            from {");
            out.println("                opacity: 0;");
            out.println("                transform: translateY(30px);");
            out.println("            }");
            out.println("            to {");
            out.println("                opacity: 1;");
            out.println("                transform: translateY(0);");
            out.println("            }");
            out.println("        }");
            out.println("        ");
            out.println("        .producto-card {");
            out.println("            animation: fadeInUp 0.6s ease-out;");
            out.println("            animation-fill-mode: both;");
            out.println("        }");
            out.println("        ");
            out.println("        @media (max-width: 768px) {");
            out.println("            .productos-container {");
            out.println("                margin: 10px;");
            out.println("                padding: 15px;");
            out.println("            }");
            out.println("            ");
            out.println("            .productos-grid {");
            out.println("                grid-template-columns: 1fr;");
            out.println("            }");
            out.println("            ");
            out.println("            .page-title {");
            out.println("                font-size: 2em;");
            out.println("            }");
            out.println("        }");
            out.println("    </style>");
            out.println("</head>");
            out.println("<body>");
            out.println("    <div class='productos-container'>");
            out.println("        <div class='header-section'>");
            out.println("            <h1 class='page-title'>🛍️ Catálogo de Productos</h1>");
            
            if (usernameOptional.isPresent()) {
                out.println("            <div class='welcome-message'>");
                out.println("                👋 ¡Hola " + usernameOptional.get() + "! Bienvenido a nuestra tienda");
                out.println("            </div>");
            } else {
                out.println("            <div class='guest-message'>");
                out.println("                ℹ️ Inicia sesión para ver precios y agregar productos al carrito");
                out.println("            </div>");
            }
            
            out.println("        </div>");
            
            if (productos.isEmpty()) {
                out.println("        <div style='text-align: center; padding: 50px;'>");
                out.println("            <h2 style='color: #666;'>😔 No hay productos disponibles</h2>");
                out.println("        </div>");
            } else {
                out.println("        <div class='productos-grid'>");
                
                for (int i = 0; i < productos.size(); i++) {
                    Producto p = productos.get(i);
                    String delay = String.valueOf(i * 0.1) + "s";
                    
                    out.println("            <div class='producto-card' style='animation-delay: " + delay + ";'>");
                    out.println("                <div class='producto-id'>#" + p.getId() + "</div>");
                    out.println("                <div class='producto-icon'>📦</div>");
                    out.println("                <h3 class='producto-nombre'>" + p.getNombre() + "</h3>");
                    out.println("                <span class='producto-tipo'>" + p.getTipo() + "</span>");
                    
                    if (usernameOptional.isPresent()) {
                        out.println("                <div class='producto-precio'>$" + String.format("%,d", p.getPrecio()) + "</div>");
                        out.println("                <a href='" + req.getContextPath() + "/carro/agregar?id=" + p.getId() + "' class='btn-agregar'>");
                        out.println("                    🛒 Agregar al Carrito");
                        out.println("                </a>");
                    } else {
                        out.println("                <div style='text-align: center; padding: 20px; color: #999;'>");
                        out.println("                    🔒 Inicia sesión para ver el precio");
                        out.println("                </div>");
                    }
                    
                    out.println("            </div>");
                }
                
                out.println("        </div>");
            }
            
            out.println("        <div class='navigation-section'>");
            out.println("            <a href='" + req.getContextPath() + "/carro' class='nav-btn'>🛒 Ver Carrito</a>");
            
            if (usernameOptional.isPresent()) {
                out.println("            <a href='" + req.getContextPath() + "/logout' class='nav-btn'>🚪 Cerrar Sesión</a>");
            } else {
                out.println("            <a href='" + req.getContextPath() + "/login' class='nav-btn'>🔐 Iniciar Sesión</a>");
            }
            
            out.println("            <a href='" + req.getContextPath() + "/index.html' class='nav-btn'>🏠 Inicio</a>");
            out.println("        </div>");
            out.println("    </div>");
            out.println("<p>" + mensajeApp + "</p>");
            out.println("<p>" + mensajeRequest + "</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}