<%-- 
    Document   : index
    Created on : 13 feb 2025, 9:16:39
    Author     : Eduardo Olalde
--%>

<%@page import="java.util.List"%>
<%@page import="com.ecofield.modelos.Rol"%>
<%@page import="com.ecofield.modelos.Usuario"%>
<%
    HttpSession sesion = request.getSession(false);
    if (sesion == null || sesion.getAttribute("usuario") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    Usuario usuario = (Usuario) sesion.getAttribute("usuario");
    List<Rol> roles = usuario.getRoles();

    if (!usuario.isHabilitado()) {
        session.setAttribute("mensaje", "La cuenta está deshabilitada. Contacta con el administrador.");
    }
%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>EcoField - Dashboard</title>
        <link rel="stylesheet" href="estilos/estiloGeneral.css">
        <script src="scripts/scriptMuestraSecciones.js"></script>
    </head>
    <body>
        <nav class="main-nav">
            <div class="nav-left">
                <img src="imagenes/EcoField.svg" alt="EcoField Logo" class="logo">
                <span class="app-name">EcoField</span>
            </div>
            <div class="nav-right">
                <% if (usuario.isHabilitado()) { %>
                <% for (Rol rol : roles) { %>
                <button class="nav-button" onclick="showSection('sec_<%= rol.getNombre().toLowerCase() %>')"><%= rol.getNombre() %></button>
                <% } %>
                <% } %>
                <button class="nav-button" onclick="showSection('sec_usuario')">Panel de usuario</button>
                <a href="LogoutServlet" class="nav-button">Cerrar Sesión</a>
            </div>
        </nav>

        <div class="main-content">
            <section class="content-area">
                <div id="mensajeExito">
                    <% if (session.getAttribute("mensaje") != null) { %>
                    <%= session.getAttribute("mensaje") %>
                    <% session.removeAttribute("mensaje"); %>
                    <% } %>
                </div>

                <div id="mensajeError">
                    <% if (session.getAttribute("error") != null) { %>
                    <%= session.getAttribute("error") %>
                    <% session.removeAttribute("error"); %>
                    <% } %>
                </div>

                <!-- Sección de usuario -->
                <div id="sec_usuario" class="section-content" style="display: none;">
                    <h2>Panel de Usuario</h2>
                    <button class="nav-button sub-section-btn" onclick="showSubSection('usuario_modDatos')">Modificar Datos</button>
                    <div id="usuario_modDatos" class="sub-section-content" style="display: none;">
                        <jsp:include page="panel_usuario.jsp" />
                    </div>
                </div>

                <!-- Secciones por rol -->
                <% for (Rol rol : roles) { 
                    String menuPath = rol.getNombre().toLowerCase() + "/menu.jsp"; 
                %>
                <div id="sec_<%= rol.getNombre().toLowerCase() %>" class="section-content" style="display: none;">
                    <h2><%= rol.getNombre() %></h2>
                    <jsp:include page="<%= menuPath %>" />
                </div>
                <% } %>
            </section>
        </div>

        <footer>
            <p>&copy; 2025 EcoField. Todos los derechos reservados.</p>
        </footer>
    </body>
</html>
