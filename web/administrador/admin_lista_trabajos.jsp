<%-- 
    Document   : admin_lista_trabajos
    Created on : 26 feb 2025, 18:43:43
    Author     : diego
--%>

<%@page import="com.ecofield.modelos.Usuario"%>
<%@page import="com.ecofield.modelos.TipoTrabajo"%>
<%@page import="com.ecofield.modelos.Trabajo"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Trabajo> trabajos = (List<Trabajo>) request.getAttribute("listaTrabajos");
    List<TipoTrabajo> tiposTrabajo = (List<TipoTrabajo>) request.getAttribute("tiposTrabajo");
    List<Usuario> agricultores = (List<Usuario>) request.getAttribute("agricultores");
    String agricultorSeleccionado = request.getParameter("agricultor");
    String tipoTrabajoSeleccionado = request.getParameter("listaTrabajostipoTrabajo");
%>
<h2>Listado de Trabajos</h2>

<!-- Formulario de Filtros -->
<form method="GET" action="dashboard" onsubmit="guardarSeccionActiva('sec_administrador', 'admin_lista_trabajos')">
    <div class="fila">
        <div class="columna">
            <label for="agricultor">Filtrar por Agricultor:</label>
            <select name="agricultor" id="agricultor">
                <option value="">Todos</option>
                <%
                    if (agricultores != null) {
                        for (Usuario agricultor : agricultores) {
                            String seleccionado = (agricultorSeleccionado != null && agricultorSeleccionado.equals(String.valueOf(agricultor.getId()))) ? "selected" : "";
                %>
                <option value="<%= agricultor.getId()%>" <%= seleccionado%>>
                    <%= agricultor.getNombre()%>
                </option>
                <%
                        }
                    }
                %>
            </select>
        </div>

        <div class="columna">
            <label for="listaTrabajostipoTrabajo">Filtrar por Tipo de Trabajo:</label>
            <select name="listaTrabajostipoTrabajo" id="listaTrabajostipoTrabajo">
                <option value="">Todos</option>
                <%
                    if (tiposTrabajo != null) {
                        for (TipoTrabajo tipo : tiposTrabajo) {
                            String seleccionado = (tipoTrabajoSeleccionado != null && tipoTrabajoSeleccionado.equals(String.valueOf(tipo.getIdTipoTrabajo()))) ? "selected" : "";
                %>
                <option value="<%= tipo.getIdTipoTrabajo()%>" <%= seleccionado%>>
                    <%= tipo.getNombre()%>
                </option>
                <%
                        }
                    }
                %>
            </select>
        </div>

        <div class="columna">
            <label for="listaTrabajosOrden">Ordenar por Fecha:</label>
            <select name="listaTrabajosOrden" id="listaTrabajosOrden">
                <option value="desc" <%= "desc".equals(request.getParameter("listaTrabajosOrden")) ? "selected" : "" %>>Más reciente</option>
                <option value="asc" <%= "asc".equals(request.getParameter("listaTrabajosOrden")) ? "selected" : "" %>>Más antiguo</option>
            </select>
        </div>
    </div>
    <button type="submit">Aplicar Filtros</button>
</form>

<!-- Tabla de Trabajos -->
<% if (trabajos != null && !trabajos.isEmpty()) { %>
<table>
    <thead>
        <tr>
            <th>ID Trabajo</th>
            <th>Número de Parcela</th>
            <th>Agricultor</th>
            <th>Tipo de Trabajo</th>
            <th>Estado</th>
            <th>Fecha Inicio</th>
        </tr>
    </thead>
    <tbody>
        <%
            for (Trabajo trabajo : trabajos) {
        %>
        <tr>
            <td><%= trabajo.getId()%></td>
            <td><%= trabajo.getNumParcela()%></td>
            <td>
                <%
                    // Buscar el nombre del agricultor (propietario) por su ID
                    String nombreAgricultor = "Desconocido";
                    for (Usuario agricultor : agricultores) {
                        if (agricultor.getId() == trabajo.getIdPropietario()) { // Suponiendo que 'idPropietario' es el propietario
                            nombreAgricultor = agricultor.getNombre();
                            break;
                        }
                    }
                %>
                <%= nombreAgricultor%>
            </td>
            <td>
                <%
                    // Buscar el nombre del tipo de trabajo por su ID
                    String nombreTipoTrabajo = "Desconocido";
                    for (TipoTrabajo tipo : tiposTrabajo) {
                        if (tipo.getIdTipoTrabajo() == trabajo.getTipoTrabajo()) {
                            nombreTipoTrabajo = tipo.getNombre();
                            break;
                        }
                    }
                %>
                <%= nombreTipoTrabajo%>
            </td>
            <td><%= trabajo.getEstado()%></td>
            <td><%= trabajo.getFecInicio()%></td>
        </tr>
        <%
            }
        %>
    </tbody>
</table>
<% } else { %>
<p>No se encontraron trabajos.</p>
<% }%>