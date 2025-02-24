<%-- 
    Document   : registro
    Created on : 24 feb 2025, 9:35:30
    Author     : Eduardo Olalde
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    HttpSession sesion = request.getSession();
    String error = (String) sesion.getAttribute("error");
    if (error != null) {
        sesion.removeAttribute("error"); // Eliminamos el mensaje después de mostrarlo
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro</title>
    <link rel="stylesheet" href="estilos/estiloLogin.css">
</head>
<body>
    <div class="form-container">
        <form action="ServletRegistro" method="POST">
            <h2>Registro de usuario</h2>

            <% if (error != null) { %>
                <p style="color: red;"><%= error %></p>
            <% } %>

            <label for="nombre">Nombre:</label>
            <input type="text" id="nombre" name="nombre" required><br><br>

            <label for="contrasenia">Contraseña:</label>
            <input type="password" id="contrasenia" name="contrasenia" required><br><br>

            <label for="telefono">Teléfono:</label>
            <input type="text" id="telefono" name="telefono" required><br><br>

            <label for="email">Correo electrónico:</label>
            <input type="email" id="email" name="email" required><br><br>

            <button type="submit">Registrar</button>
        </form>

        <button class="volver-btn" onclick="window.location.href='login.jsp'">Volver al Login</button>
    </div>
</body>
</html>
