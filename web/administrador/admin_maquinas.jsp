<%-- 
    Document   : admin_maquinas
    Created on : 26 feb 2025, 18:43:23
    Author     : diego
--%>

<%@page import="com.ecofield.modelos.Maquina"%>
<%@page import="com.ecofield.modelos.TipoTrabajo"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Obtener las máquinas y los tipos de trabajo desde la request
    List<Maquina> maquinas = (List<Maquina>) request.getAttribute("maquinas");
    List<TipoTrabajo> tiposTrabajo = (List<TipoTrabajo>) request.getAttribute("tiposTrabajo");
    String filtroTipoMaquina = request.getParameter("tipo_maquina_filtro");
    String filtroEstado = request.getParameter("estado_filtro");
%>

<h2>Gestión de Máquinas</h2>

<!-- Filtro de máquinas -->
<h3>Filtrar Máquinas</h3>
<form method="GET" action="dashboard" onsubmit="guardarSeccionActiva('sec_administrador', 'admin_maquinas')">
    <label for="tipo_maquina_filtro">Filtrar por Tipo de Máquina:</label>
    <select name="tipo_maquina_filtro" id="tipo_maquina_filtro">
        <option value="">Todos</option>
        <% for (TipoTrabajo tipoTrabajo : tiposTrabajo) {%>
        <option value="<%= tipoTrabajo.getIdTipoTrabajo()%>" <%= (filtroTipoMaquina != null && filtroTipoMaquina.equals(String.valueOf(tipoTrabajo.getIdTipoTrabajo()))) ? "selected" : ""%> >
            <%= tipoTrabajo.getNombre()%>
        </option>
        <% }%>
    </select>

    <label for="estado_filtro">Filtrar por Estado:</label>
    <select name="estado_filtro" id="estado_filtro">
        <option value="">Todos</option>
        <option value="Disponible" <%= (filtroEstado != null && filtroEstado.equals("Disponible")) ? "selected" : ""%> >Disponible</option>
        <option value="Asignada" <%= (filtroEstado != null && filtroEstado.equals("Asignada")) ? "selected" : ""%> >Asignada</option>
        <option value="Fuera de servicio" <%= (filtroEstado != null && filtroEstado.equals("Fuera de servicio")) ? "selected" : ""%> >Fuera de servicio</option>
    </select>

    <input type="submit" value="Filtrar">
</form>

<!-- Mostrar las máquinas -->
<h3>Máquinas Registradas</h3>
<% if (maquinas != null && !maquinas.isEmpty()) { %>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Estado</th>
        <th>Tipo</th>
        <th>Modelo</th>
        <th>Acciones</th>
        <th>Cambiar estado</th>
    </tr>
    <% for (Maquina maquina : maquinas) {%>
    <tr>
        <!-- Formulario para eliminar máquina -->
    <form method="POST" action="AdminMaquinaServlet" onsubmit="guardarSeccionActiva('sec_administrador', 'admin_maquinas')">
        <input type="hidden" name="accion" value="eliminar_maquina">
        <input type="hidden" name="id_maquina" value="<%= maquina.getIdMaquina()%>">
        <td><%= maquina.getIdMaquina()%></td>
        <td><%= maquina.getEstado()%></td>
        <td><%= maquina.getTipoMaquina()%></td>
        <td><%= maquina.getModelo()%></td>
        <td>
            <button type="submit">Eliminar</button>
        </td>
    </form>

    <!-- Formulario para cambiar el estado de la máquina -->
    <form method="POST" action="AdminMaquinaServlet" onsubmit="guardarSeccionActiva('sec_administrador', 'admin_maquinas')">
        <input type="hidden" name="accion" value="actualizar_estado_maquina">
        <input type="hidden" name="id_maquina" value="<%= maquina.getIdMaquina()%>">
        <td>
            <select name="estado_maquina" onchange="this.form.submit()">
                <option value="Disponible" <%= maquina.getEstado().equals("Disponible") ? "selected" : ""%>>Disponible</option>
                <option value="Asignada" <%= maquina.getEstado().equals("Asignada") ? "selected" : ""%>>Asignada</option>
                <option value="Fuera de servicio" <%= maquina.getEstado().equals("Fuera de servicio") ? "selected" : ""%>>Fuera de servicio</option>
            </select>
        </td>
    </form>
</tr>
<% } %>
</table>
<% } else { %>
<p>No se encontraron máquinas.</p>
<% } %>

<!-- Formulario para agregar una nueva máquina -->
<h3>Agregar Nueva Máquina</h3>
<form method="POST" action="AdminMaquinaServlet" onsubmit="guardarSeccionActiva('sec_administrador', 'admin_maquinas')">
    <input type="hidden" name="accion" value="agregar_maquina">
    <label for="estado_ad_maq">Estado:</label>
    <select name="estado_ad_maq" id="estado_ad_maq">
        <option value="Disponible">Disponible</option>
        <option value="Asignada">Asignada</option>
        <option value="Fuera de servicio">Fuera de servicio</option>
    </select><br>

    <label for="tipo_trabajo_ad_maq">Tipo de Máquina:</label>
    <select name="tipo_trabajo_ad_maq" id="tipo_trabajo_ad_maq">
        <option value="">Seleccione un tipo</option>
        <% for (TipoTrabajo tipoTrabajo : tiposTrabajo) {%>
        <option value="<%= tipoTrabajo.getIdTipoTrabajo()%>"><%= tipoTrabajo.getNombre()%></option>
        <% }%>
    </select><br>

    <label for="modelo">Modelo:</label>
    <input type="text" name="modelo" id="modelo" required><br>

    <input type="submit" name="agregar_maquina" value="Agregar Máquina">
</form>
