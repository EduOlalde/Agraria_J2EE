<%-- 
    Document   : panel_usuario
    Created on : 25 feb 2025, 20:47:43
    Author     : Eduardo Olalde
--%>

<%@page import="com.ecofield.modelos.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    HttpSession sesion = request.getSession();
    Usuario usuario = (Usuario) sesion.getAttribute("usuario");

    if (usuario == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<div class="sidebar">
    <h2>Modificar Datos</h2>
    <form action="PanelUsuarioServlet" method="post" onsubmit="guardarSeccionActiva('sec_usuario', '')">
        <label for="nombre">Contraseña actual:</label>
        <input type="text" name="passActual">
        <br>
        
        <label for="nombre">Nombre:</label>
        <input type="text" name="nombre" value="<%= usuario.getNombre() %>">

        <label for="contrasenia">Nueva Contraseña (opcional):</label>
        <input type="password" name="contrasenia">

        <label for="repetir_contrasenia">Repetir Contraseña:</label>
        <input type="password" name="repetir_contrasenia">

        <label for="telefono">Teléfono:</label>
        <input type="text" name="telefono" value="<%= usuario.getTelefono() %>">

        <label for="email">Email:</label>
        <input type="email" name="email" value="<%= usuario.getEmail() %>">

        <button type="submit" name='modificar_datos_usuario'>Guardar Cambios</button>
    </form>
</div>
