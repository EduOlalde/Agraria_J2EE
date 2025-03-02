<%-- 
    Document   : admin_lista_trabajos
    Created on : 26 feb 2025, 18:43:43
    Author     : diego
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.ecofield.modelos.Usuario"%>
<%@page import="com.ecofield.modelos.TipoTrabajo"%>
<%@page import="com.ecofield.modelos.Trabajo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Trabajo> trabajosPendientes = (List<Trabajo>) request.getAttribute("adminTrabajosPendientes");
    List<Trabajo> trabajosEnCurso = (List<Trabajo>) request.getAttribute("adminTrabajosEnCurso");
    List<Trabajo> trabajosFinalizados = (List<Trabajo>) request.getAttribute("adminTrabajosFinalizados");
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
                <option value="desc" <%= "desc".equals(request.getParameter("listaTrabajosOrden")) ? "selected" : ""%>>Más reciente</option>
                <option value="asc" <%= "asc".equals(request.getParameter("listaTrabajosOrden")) ? "selected" : ""%>>Más antiguo</option>
            </select>
        </div>
    </div>
    <button type="submit">Aplicar Filtros</button>
</form>

<%-- Trabajos Pendientes --%>
<% if (trabajosPendientes != null && !trabajosPendientes.isEmpty()) { %>
<h3>Trabajos Pendientes</h3>
<table border="1">
    <thead>
        <tr>
            <th>ID Trabajo</th>
            <th>Número de Parcela</th>
            <th>Agricultor</th>
            <th>Tipo de Trabajo</th>
        </tr>
    </thead>
    <tbody>
        <%
            for (Trabajo trabajo : trabajosPendientes) {
                int idAgricultor = trabajo.getIdPropietario(); // Asumiendo que el ID del agricultor está en el campo "idPropietario" del trabajo.
                String nombreAgricultor = "";
                for (Usuario agricultor : agricultores) {
                    if (agricultor.getId() == idAgricultor) {
                        nombreAgricultor = agricultor.getNombre();
                        break;
                    }
                }

                String nombreTipoTrabajo = "";
                for (TipoTrabajo tipo : tiposTrabajo) {
                    if (tipo.getIdTipoTrabajo() == trabajo.getTipoTrabajo()) {
                        nombreTipoTrabajo = tipo.getNombre();
                        break;
                    }
                }
        %>
        <tr>
            <td><%= trabajo.getId()%></td>
            <td><%= trabajo.getNumParcela()%></td>
            <td><%= nombreAgricultor%></td>
            <td><%= nombreTipoTrabajo%></td>
        </tr>
        <%
            }
        %>
    </tbody>
</table>
<% } else { %>
<p>No hay trabajos pendientes.</p>
<% } %>

<%-- Trabajos en Curso --%>
<% if (trabajosEnCurso != null && !trabajosEnCurso.isEmpty()) { %>
<h3>Trabajos En Curso</h3>
<table border="1">
    <thead>
        <tr>
            <th>ID Trabajo</th>
            <th>Número de Parcela</th>
            <th>Agricultor</th>
            <th>Tipo de Trabajo</th>
            <th>Fecha Inicio</th>
        </tr>
    </thead>
    <tbody>
        <%
            for (Trabajo trabajo : trabajosEnCurso) {
                int idAgricultor = trabajo.getIdPropietario();
                String nombreAgricultor = "";
                for (Usuario agricultor : agricultores) {
                    if (agricultor.getId() == idAgricultor) {
                        nombreAgricultor = agricultor.getNombre();
                        break;
                    }
                }

                String nombreTipoTrabajo = "";
                for (TipoTrabajo tipo : tiposTrabajo) {
                    if (tipo.getIdTipoTrabajo() == trabajo.getTipoTrabajo()) {
                        nombreTipoTrabajo = tipo.getNombre();
                        break;
                    }
                }
        %>
        <tr>
            <td><%= trabajo.getId()%></td>
            <td><%= trabajo.getNumParcela()%></td>
            <td><%= nombreAgricultor%></td>
            <td><%= nombreTipoTrabajo%></td>
            <td><%= trabajo.getFecInicio()%></td>
        </tr>
        <%
            }
        %>
    </tbody>
</table>
<% } else { %>
<p>No hay trabajos en curso.</p>
<% } %>

<%-- Trabajos Finalizados --%>
<% if (trabajosFinalizados != null && !trabajosFinalizados.isEmpty()) { %>
<h3>Trabajos Finalizados</h3>
<table border="1">
    <thead>
        <tr>
            <th>ID Trabajo</th>
            <th>Número de Parcela</th>
            <th>Agricultor</th>
            <th>Tipo de Trabajo</th>
            <th>Fecha Inicio</th>
            <th>Fecha Fin</th>
        </tr>
    </thead>
    <tbody>
        <%
            for (Trabajo trabajo : trabajosFinalizados) {
                int idAgricultor = trabajo.getIdPropietario();
                String nombreAgricultor = "";
                for (Usuario agricultor : agricultores) {
                    if (agricultor.getId() == idAgricultor) {
                        nombreAgricultor = agricultor.getNombre();
                        break;
                    }
                }

                String nombreTipoTrabajo = "";
                for (TipoTrabajo tipo : tiposTrabajo) {
                    if (tipo.getIdTipoTrabajo() == trabajo.getTipoTrabajo()) {
                        nombreTipoTrabajo = tipo.getNombre();
                        break;
                    }
                }
        %>
        <tr>
            <td><%= trabajo.getId()%></td>
            <td><%= trabajo.getNumParcela()%></td>
            <td><%= nombreAgricultor%></td>
            <td><%= nombreTipoTrabajo%></td>
            <td><%= trabajo.getFecInicio()%></td>
            <td><%= trabajo.getFecFin()%></td>
        </tr>
        <%
            }
        %>
    </tbody>
</table>
<% } else { %>
<p>No hay trabajos finalizados.</p>
<% }%>

