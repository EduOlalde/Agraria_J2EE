<%-- 
    Document   : login
    Created on : 24 feb 2025, 9:35:23
    Author     : Eduardo Olalde
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="estilos/estiloLogin.css">
</head>
<body>
    <nav class="main-nav">
        <div class="nav-left">
            <img src="imagenes/EcoField.svg" alt="EcoField Logo" class="logo">
            <span class="app-name">EcoField</span>
        </div>
    </nav>

    <div class="form-container">
        <% if (session.getAttribute("error") != null) { %>
            <div class="error-message">
                <%= session.getAttribute("error") %>
                <% session.removeAttribute("error"); %>
            </div>
        <% } %>
        <% if (session.getAttribute("mensaje") != null) { %>
            <div class="success-message">
                <%= session.getAttribute("mensaje") %>
                <% session.removeAttribute("mensaje"); %>
            </div>
        <% } %>

        <form action="LoginServlet" method="POST">
            <h2>Iniciar sesión</h2>
            <label for="username">Usuario:</label>
            <input type="text" id="username" name="username" required><br><br>
            
            <label for="password">Contraseña:</label>
            <input type="password" id="password" name="password" required><br><br>
            
            <button type="submit">Iniciar sesión</button>
        </form>

        <button type="button" onclick="window.location.href='registro.jsp'">¿No tienes cuenta? Regístrate aquí</button>
    </div>
</body>
</html>
