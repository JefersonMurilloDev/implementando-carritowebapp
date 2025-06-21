<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Listado de Productos</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.css?v=${System.currentTimeMillis()}">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body>
<div class="container my-4">
    <div class="card">
        <div class="card-header">
            <h1>Listado de Productos</h1>
        </div>
        <div class="card-body">
            <c:if test="${username.isPresent()}">
                <div class="alert alert-success">Hola ${username.get()}, bienvenido</div>
            </c:if>
            <table class="table table-hover table-striped">
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Nombre</th>
                    <th>Tipo</th>
                    <c:if test="${username.isPresent()}">
                        <th>Precio</th>
                        <th>Agregar al Carro</th>
                    </c:if>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${productos}" var="p">
                    <tr>
                        <td>${p.id}</td>
                        <td>${p.nombre}</td>
                        <td>${p.tipo}</td>
                        <c:if test="${username.isPresent()}">
                            <td>$${p.precio}</td>
                            <td>
                                <a class="btn btn-primary btn-sm" href="${pageContext.request.contextPath}/carro/agregar?id=${p.id}">
                                    <i class="fas fa-shopping-cart"></i> Agregar
                                </a>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="card-footer">
            <c:if test="${not empty applicationScope.mensaje}">
                <div class="alert alert-info">${applicationScope.mensaje}</div>
            </c:if>
            <c:if test="${not empty requestScope.mensaje}">
                <div class="alert alert-info">${requestScope.mensaje}</div>
            </c:if>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/index.html">Volver</a>
        </div>
    </div>
</div>
</body>
</html>