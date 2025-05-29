<%@page contentType="text/html" pageEncoding="UTF-8" import="org.jmurillo.apiservlet.webapp.session.models.*"%>
<%
Carro carro = (Carro) session.getAttribute("carro");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Carro de Compras</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/style.css">
    <style>
        .carro-container {
            max-width: 1000px;
            margin: 20px auto;
            padding: 20px;
        }
        
        .carro-table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            border-radius: 8px;
            overflow: hidden;
        }
        
        .carro-table th {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 15px;
            text-align: left;
            font-weight: 600;
        }
        
        .carro-table td {
            padding: 12px 15px;
            border-bottom: 1px solid #eee;
        }
        
        .carro-table tr:hover {
            background-color: #f8f9fa;
        }
        
        .cantidad-input {
            width: 60px;
            padding: 5px;
            border: 1px solid #ddd;
            border-radius: 4px;
            text-align: center;
        }
        
        .checkbox-eliminar {
            transform: scale(1.2);
            cursor: pointer;
        }
        
        .botones-accion {
            margin: 20px 0;
            display: flex;
            gap: 10px;
            flex-wrap: wrap;
        }
        
        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            font-size: 14px;
            transition: all 0.3s ease;
        }
        
        .btn-actualizar {
            background: linear-gradient(135deg, #4CAF50, #45a049);
            color: white;
        }
        
        .btn-eliminar {
            background: linear-gradient(135deg, #f44336, #da190b);
            color: white;
        }
        
        .btn-continuar {
            background: linear-gradient(135deg, #2196F3, #1976D2);
            color: white;
        }
        
        .btn-volver {
            background: linear-gradient(135deg, #FF9800, #F57C00);
            color: white;
        }
        
        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.2);
        }
        
        .total-row {
            background-color: #f8f9fa;
            font-weight: bold;
            font-size: 16px;
        }
        
        .empty-cart {
            text-align: center;
            padding: 40px;
            color: #666;
            font-size: 18px;
        }
        
        .precio {
            color: #28a745;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="carro-container">
        <h1>🛒 Carro de Compras</h1>
        
        <% if(carro == null || carro.getItems().isEmpty()){ %>
            <div class="empty-cart">
                <p>😔 Lo sentimos, no hay productos en el carro de compras</p>
            </div>
        <% } else { %>
            <form action="<%=request.getContextPath()%>/actualizar-carro" method="post">
                <table class="carro-table">
                    <thead>
                        <tr>
                            <th>Eliminar</th>
                            <th>ID</th>
                            <th>Producto</th>
                            <th>Precio</th>
                            <th>Cantidad</th>
                            <th>Total</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% int index = 0; %>
                        <% for(ItemCarro item: carro.getItems()){ %>
                            <tr>
                                <td>
                                    <input type="checkbox" name="eliminar" value="<%=item.getProducto().getId()%>" class="checkbox-eliminar">
                                </td>
                                <td><%=item.getProducto().getId()%></td>
                                <td><%=item.getProducto().getNombre()%></td>
                                <td class="precio">$<%=String.format("%,d", item.getProducto().getPrecio())%></td>
                                <td>
                                    <input type="number" 
                                           name="cantidad" 
                                           value="<%=item.getCantidad()%>" 
                                           min="0" 
                                           max="99" 
                                           class="cantidad-input">
                                    <input type="hidden" name="productoId" value="<%=item.getProducto().getId()%>">
                                </td>
                                <td class="precio">$<%=String.format("%,d", item.obtenerImporte())%></td>
                            </tr>
                            <% index++; %>
                        <% } %>
                        <tr class="total-row">
                            <td colspan="5" style="text-align: right; padding-right: 20px;">
                                <strong>TOTAL GENERAL:</strong>
                            </td>
                            <td class="precio">
                                <strong>$<%=String.format("%,d", carro.obtenerImporteTotal())%></strong>
                            </td>
                        </tr>
                    </tbody>
                </table>
                
                <div class="botones-accion">
                    <button type="submit" name="accion" value="actualizar" class="btn btn-actualizar">
                        🔄 Actualizar Cantidades
                    </button>
                    <button type="submit" name="accion" value="eliminar" class="btn btn-eliminar">
                        🗑️ Eliminar Seleccionados
                    </button>
                </div>
            </form>
        <% } %>
        
        <div class="botones-accion">
            <a href="<%=request.getContextPath()%>/productos" class="btn btn-continuar">
                🛍️ Seguir Comprando
            </a>
            <a href="<%=request.getContextPath()%>/index.html" class="btn btn-volver">
                🏠 Volver al Inicio
            </a>
        </div>
    </div>
</body>
</html>