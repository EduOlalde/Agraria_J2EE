<%@page import="com.ecofield.modelos.TipoTrabajo"%>
<%@page import="com.ecofield.modelos.Trabajo"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Se espera que el servlet haya cargado la lista de trabajos y tipos de trabajo
    List<Trabajo> trabajos = (List<Trabajo>) request.getAttribute("listaTrabajos");
    List<TipoTrabajo> tiposTrabajo = (List<TipoTrabajo>) request.getAttribute("tiposTrabajo");
    
    // Recuperar filtros enviados por GET
    String tipoTrabajoSeleccionado = request.getParameter("tipoTrabajo");
    String fechaInicio = request.getParameter("fecha_inicio");
    String fechaFin = request.getParameter("fecha_fin");
    String orden = request.getParameter("orden");
%>
<h2>Mis Trabajos</h2>

<!-- Formulario de Filtros -->
<form method="GET" action="ListarTrabajosAgricultor" onsubmit="guardarSeccionActiva('sec_agricultor', 'listado_trabajos_agricultor')">
    <div class="fila">
        <div class="columna">
            <label for="tipoTrabajo">Filtrar por Tipo de Trabajo:</label>
            <select name="tipoTrabajo" id="tipoTrabajo">
                <option value="">Todos</option>
                <%
                    if (tiposTrabajo != null) {
                        for (TipoTrabajo tipo : tiposTrabajo) {
                            String seleccionado = (tipoTrabajoSeleccionado != null && tipoTrabajoSeleccionado.equals(String.valueOf(tipo.getIdTipoTrabajo()))) ? "selected" : "";
                %>
                <option value="<%= tipo.getIdTipoTrabajo() %>" <%= seleccionado %>><%= tipo.getNombre() %></option>
                <%
                        }
                    }
                %>
            </select>
        </div>
        <div class="columna">
            <label for="fecha_inicio">Fecha Inicio Desde:</label>
            <input type="date" name="fecha_inicio" id="fecha_inicio" value="<%= fechaInicio != null ? fechaInicio : "" %>">
        </div>
        <div class="columna">
            <label for="fecha_fin">Fecha Fin Hasta:</label>
            <input type="date" name="fecha_fin" id="fecha_fin" value="<%= fechaFin != null ? fechaFin : "" %>">
        </div>
        <div class="columna">
            <label for="orden">Ordenar por Fecha:</label>
            <select name="orden" id="orden">
                <option value="desc" <%= (orden != null && orden.equals("desc")) ? "selected" : "" %>>Más reciente</option>
                <option value="asc" <%= (orden != null && orden.equals("asc")) ? "selected" : "" %>>Más antiguo</option>
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
            <th>Tipo de Trabajo</th>
            <th>Estado</th>
            <th>Fecha Inicio</th>
            <th>Fecha Fin</th>
            <th>Horas</th>
        </tr>
    </thead>
    <tbody>
        <% for (Trabajo trabajo : trabajos) { %>
        <tr>
            <td><%= trabajo.getId() %></td>
            <td><%= trabajo.getNumParcela() %></td>
            <td>
                <%
                    String nombreTipoTrabajo = "Desconocido";
                    if (tiposTrabajo != null) {
                        for (TipoTrabajo tipo : tiposTrabajo) {
                            if (tipo.getIdTipoTrabajo() == trabajo.getTipoTrabajo()) {
                                nombreTipoTrabajo = tipo.getNombre();
                                break;
                            }
                        }
                    }
                %>
                <%= nombreTipoTrabajo %>
            </td>
            <td><%= trabajo.getEstado() %></td>
            <td><%= trabajo.getFecInicio() %></td>
            <td><%= trabajo.getFecFin() %></td>
            <td><%= trabajo.getHoras() %></td>
        </tr>
        <% } %>
    </tbody>
</table>
<% } else { %>
<p>No se encontraron trabajos.</p>
<% } %>
