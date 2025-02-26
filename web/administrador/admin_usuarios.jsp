<%-- 
    Document   : admin_usuarios
    Created on : 26 feb 2025, 16:33:53
    Author     : Eduardo Olalde
--%>

<%@page import="com.ecofield.dao.UsuarioDAO"%>
<%@page import="com.ecofield.dao.RolDAO"%>
<%@page import="com.mysql.jdbc.Connection"%>
<%@page import="com.ecofield.modelos.Rol"%>
<%@page import="java.util.List"%>
<%@page import="com.ecofield.modelos.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Connection conn = (Connection) session.getAttribute("conexion");
    RolDAO rolDAO = new RolDAO(conn);
    UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
    List<Usuario> usuarios = usuarioDAO.listarUsuarios();
    List<Rol> rolesDisponibles = rolDAO.obtenerRolesDisponibles();

%>
<h3>Nuevo Usuario</h3>
<form method="POST" action="AdminUsuarioServlet" onsubmit="guardarSeccionActiva('sec_administrador', 'admin_usuarios')">
    <input type="hidden" name="accion" value="crear">
    <label>Nombre:</label>
    <input type="text" name="nombre" required>
    <label>Email:</label>
    <input type="email" name="email" required>
    <label>Teléfono:</label>
    <input type="text" name="telefono">
    <label>Contraseña:</label>
    <input type="password" name="contrasenia" required>
    <label>Habilitado:</label>
    <input type="checkbox" name="habilitado">
    <label>Roles:</label><br>
    <% for (Rol rol : rolesDisponibles) {%>
    <label>
        <input type="checkbox" name="roles" value="<%= rol.getIdRol()%>">
        <%= rol.getNombre()%>
    </label><br>
    <% } %>
    <button type="submit">Registrar Usuario</button>
</form>
<hr>

<table border="1">
    <tr>
        <th>Nombre</th>
        <th>Email</th>
        <th>Teléfono</th>
        <th>Habilitado</th>
        <th>Roles</th>
        <th>Acciones</th>
    </tr>
    <% for (Usuario usuario : usuarios) {%>
    <tr>
    <form method="POST" action="AdminUsuarioServlet" onsubmit="guardarSeccionActiva('sec_administrador', 'admin_usuarios')">
        <input type="hidden" name="accion" value="modificar">
        <input type="hidden" name="id_usuario" value="<%= usuario.getId()%>">
        <td><input type="text" name="nombre" value="<%= usuario.getNombre()%>" required></td>
        <td><input type="email" name="email" value="<%= usuario.getEmail()%>" required></td>
        <td><input type="text" name="telefono" value="<%= usuario.getTelefono()%>"></td>
        <td><input type="checkbox" name="habilitado" <%= usuario.isHabilitado() ? "checked" : ""%>></td>
        <td>
            <% for (Rol rol : rolesDisponibles) {%>
            <label>
                <input type="checkbox" name="roles" value="<%= rol.getIdRol()%>" <%= usuario.getRoles().contains(rol.getNombre()) ? "checked" : ""%>>
                <%= rol.getNombre()%>
            </label><br>
            <% }%>
        </td>
        <td>
            <button type="submit">Actualizar</button>
        </td>
    </form>
    <form method="POST" action="AdminUsuarioServlet" onsubmit="guardarSeccionActiva('sec_administrador', 'admin_usuarios')">
        <input type="hidden" name="accion" value="eliminar">
        <input type="hidden" name="id_usuario" value="<%= usuario.getId()%>">
        <td><button type="submit">Eliminar</button></td>
    </form>
</tr>
<% }%>
</table>