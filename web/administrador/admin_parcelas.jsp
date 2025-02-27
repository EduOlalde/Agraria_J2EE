<%-- 
    Document   : admin_parcelas
    Created on : 26 feb 2025, 18:43:15
    Author     : diego
--%>

<%@page import="java.util.List"%>
<%@page import="com.ecofield.modelos.Parcela"%>
<%@page import="com.ecofield.modelos.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Obtener las parcelas y los agricultores desde la request
    List<Parcela> parcelas = (List<Parcela>) request.getAttribute("parcelas");
    List<Usuario> agricultores = (List<Usuario>) request.getAttribute("agricultores");
    String filtroAgricultor = request.getParameter("agricultor");
    String filtroExtension = request.getParameter("extension");
%>
<h3>Gestión de Parcelas</h3>

<!-- Formulario para filtros -->
<form method="GET" action="dashboard" onsubmit="guardarSeccionActiva('sec_administrador', 'admin_parcelas')">
    <label for="agricultor">Filtrar por Agricultor:</label>
    <select name="agricultor" id="lista_agricultor">
        <option value="">Todos</option>
        <% for (Usuario agricultor : agricultores) {%>
        <option value="<%= agricultor.getId()%>" <%= (filtroAgricultor != null && filtroAgricultor.equals(String.valueOf(agricultor.getId()))) ? "selected" : ""%> >
            <%= agricultor.getNombre()%>
        </option>
        <% }%>
    </select>

    <label for="extension">Filtrar por Extensión (menor que):</label>
    <input type="number" name="extension" id="extension" step="0.01" value="<%= filtroExtension != null ? filtroExtension : ""%>">

    <input type="submit" value="Filtrar">
</form>

<!-- Mostrar las parcelas -->
<h3>Parcelas Registradas</h3>
<% if (parcelas != null && !parcelas.isEmpty()) { %>
<table border="1">
    <tr>
        <th>ID Catastro</th>
        <th>Extensión</th>
        <th>Propietario</th>
        <th>Acciones</th>
    </tr>
    <% for (Parcela parcela : parcelas) {%>
    <tr>
    <form method="POST" action="AdminParcelaServlet" onsubmit="guardarSeccionActiva('sec_administrador', 'admin_parcelas')">
        <input type="hidden" name="accion" value="eliminar_parcela">
        <input type="hidden" name="id_catastro" value="<%= parcela.getIdCatastro()%>">
        <td><%= parcela.getIdCatastro()%></td>
        <td><%= parcela.getExtension()%> ha</td>
        <td>
            <%
                // Buscar el nombre del agricultor cuyo ID coincida con el propietario de la parcela
                String nombreAgricultor = "Desconocido"; 
                for (Usuario agricultor : agricultores) {
                    if (agricultor.getId() == parcela.getPropietario()) { 
                        nombreAgricultor = agricultor.getNombre();
                        break; // Salimos del bucle cuando encontramos el agricultor correspondiente
                    }
                }
            %>
            <%= nombreAgricultor%>
        </td>

        <td>
            <button type="submit">Eliminar</button>
        </td>
    </form>
</tr>
<% } %>
</table>
<% } else { %>
<p>No se encontraron parcelas.</p>
<% } %>

<!-- Formulario para agregar una nueva parcela -->
<h3>Agregar Nueva Parcela</h3>
<form method="POST" action="AdminParcelaServlet" onsubmit="guardarSeccionActiva('sec_administrador', 'admin_parcelas')">
    <label for="ID_Catastro">ID Catastro:</label>
    <input type="text" id="ID_Catastro" name="ID_Catastro" required>
    <br>

    <label for="Extension">Extensión:</label>
    <input type="number" id="Extension" name="Extension" step="0.01" required>
    <br>

    <label for="Propietario">Propietario:</label>
    <select id="Propietario" name="Propietario" required>
        <option value="">Seleccione un agricultor</option>
        <% for (Usuario agricultor : agricultores) {%>
        <option value="<%= agricultor.getId()%>"><%= agricultor.getNombre()%></option>
        <% }%>
    </select>
    <br>

    <input type="submit" name="agregar_parcela" value="Agregar Parcela">
</form>
