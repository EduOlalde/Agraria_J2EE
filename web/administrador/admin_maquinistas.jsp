<%-- 
    Document   : admin_maquinistas
    Created on : 26 feb 2025, 18:43:06
    Author     : diego
--%>

<%@page import="com.ecofield.modelos.Maquinista"%>
<%@page import="com.ecofield.modelos.TipoTrabajo"%>
<%@page import="java.util.List"%>
<%@page import="com.ecofield.modelos.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Maquinista> maquinistas = (List<Maquinista>) request.getAttribute("maquinistas");
    List<TipoTrabajo> tiposTrabajo = (List<TipoTrabajo>) request.getAttribute("tiposTrabajo");
%>

<h3>Gestión de Maquinistas</h3>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Nombre</th>
        <th>Email</th>
        <th>Especialidades</th>
        <th>Acciones</th>
    </tr>
    <% for (Maquinista maquinista : maquinistas) {%>
    <tr>
    <form method="POST" action="AdminMaquinistasServlet" onsubmit="guardarSeccionActiva('sec_administrador', 'admin_maquinistas')">
        <input type="hidden" name="accion" value="modificarEspecialidades" >
        <input type="hidden" name="id_maquinista" value="<%= maquinista.getId()%>">
        <td><%= maquinista.getId()%></td>
        <td><%= maquinista.getNombre()%></td>
        <td><%= maquinista.getEmail()%></td>
        <td>
            <% for (TipoTrabajo tipoTrabajo : tiposTrabajo) {%>
            <label>
                <input type="checkbox" name="especialidades" value="<%= tipoTrabajo.getIdTipoTrabajo()%>" 
                       <%= maquinista.getEspecialidades().contains(tipoTrabajo.getIdTipoTrabajo()) ? "checked" : ""%>> 
                <%= tipoTrabajo.getNombre()%>
            </label><br>
            <% } %>
        </td>
        <td>
            <button type="submit">Actualizar Especialidades</button>
        </td>
    </form>
</tr>
<% }%>
</table>