<%-- 
    Document   : maq_trabajos
    Created on : 26 feb 2025, 9:46:33
    Author     : Eduardo Olalde
--%>

<%@page import="com.mysql.jdbc.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.ecofield.modelos.Trabajo, com.ecofield.dao.TrabajoDAO" %>
<%@ page import="javax.servlet.http.HttpSession" %>

<%
    int idMaquinista = (int) session.getAttribute("user_id");
    Connection conn = (Connection) session.getAttribute("conexion");
    TrabajoDAO trabajoDAO = new TrabajoDAO(conn);

    // Obtener trabajos pendientes
    List<Trabajo> trabajosPendientes = trabajoDAO.obtenerTrabajosPendientes(idMaquinista);

    // Obtener trabajos en curso
    List<Trabajo> trabajosEnCurso = trabajoDAO.obtenerTrabajosEnCurso(idMaquinista);

    // Obtener historial de trabajos finalizados
    List<Trabajo> historialTrabajos = trabajoDAO.obtenerHistorialTrabajos(idMaquinista);
%>

<h2>Trabajos Pendientes</h2>
<% if (trabajosPendientes.size() > 0) { %>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Tipo</th>
        <th>Parcela</th>
        <th>Estado</th>
        <th>Acciones</th>
    </tr>
    <% for (Trabajo trabajo : trabajosPendientes) {%>
    <tr>
        <td><%= trabajo.getId()%></td>
        <td><%= trabajo.getTipoTrabajo()%></td>
        <td><%= trabajo.getNumParcela()%></td>
        <td><%= trabajo.getEstado()%></td>
        <td>
            <form method="post" action="IniciarTrabajoServlet" onsubmit="guardarSeccionActiva('sec_maquinista', 'maquinista_trabajos')">
                <input type="hidden" name="id_trabajo" value="<%= trabajo.getId()%>">
                <label>Fecha Inicio:</label>
                <input type="date" name="fecha_inicio">
                <button type="submit" name="iniciar_trabajo">Iniciar Trabajo</button>
            </form>
        </td>
    </tr>
    <% } %>
</table>
<% } else { %>
<p>No hay trabajos pendientes.</p>
<% } %>

<h2>Trabajos En Curso</h2>
<% if (trabajosEnCurso.size() > 0) { %>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Tipo</th>
        <th>Parcela</th>
        <th>Estado</th>
        <th>Acciones</th>
    </tr>
    <% for (Trabajo trabajo : trabajosEnCurso) {%>
    <tr>
        <td><%= trabajo.getId()%></td>
        <td><%= trabajo.getTipoTrabajo()%></td>
        <td><%= trabajo.getNumParcela()%></td>
        <td><%= trabajo.getEstado()%></td>
        <td>
            <form method="post" action="FinalizarTrabajoServlet" onsubmit="guardarSeccionActiva('sec_maquinista', 'maquinista_trabajos')">
                <input type="hidden" name="id_trabajo" value="<%= trabajo.getId()%>">
                <label>Fecha Fin:</label>
                <input type="date" name="fecha_fin">
                <label>Horas:</label>
                <input type="number" name="horas" min="1" step="0.1">
                <button type="submit" name="finalizar_trabajo">Finalizar Trabajo</button>
            </form>
        </td>
    </tr>
    <% } %>
</table>
<% } else { %>
<p>No hay trabajos en curso.</p>
<% } %>

<h2>Historial de Trabajos Finalizados</h2>
<% if (historialTrabajos.size() > 0) { %>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Tipo</th>
        <th>Parcela</th>
        <th>Estado</th>
        <th>Fecha Inicio</th>
        <th>Fecha Fin</th>
        <th>Horas</th>
    </tr>
    <% for (Trabajo trabajo : historialTrabajos) {%>
    <tr>
        <td><%= trabajo.getId()%></td>
        <td><%= trabajo.getTipoTrabajo()%></td>
        <td><%= trabajo.getNumParcela()%></td>
        <td><%= trabajo.getEstado()%></td>
        <td><%= trabajo.getFecInicio()%></td>
        <td><%= trabajo.getFecFin()%></td>
        <td><%= trabajo.getHoras()%></td>
    </tr>
    <% } %>
</table>
<% } else { %>
<p>No hay historial de trabajos.</p>
<% }%>
