<%@page import="com.ecofield.modelos.TrabajoSolicitado"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.ecofield.modelos.Parcela" %>
<%@ page import="com.ecofield.modelos.TipoTrabajo" %>
<%
    // Obtener las parcelas disponibles y los tipos de trabajo desde la request
    List<Parcela> parcelas = (List<Parcela>) request.getAttribute("parcelasAgricultor");
    List<TipoTrabajo> tiposTrabajo = (List<TipoTrabajo>) request.getAttribute("tiposTrabajo");
    List<TrabajoSolicitado> trabajosSolicitados = (List<TrabajoSolicitado>) request.getAttribute("trabajosSolicitados");
%>

<h2>Solicitar Trabajo</h2>
<h3>Selecciona tu Parcela y Tipo de Trabajo</h3>

<%-- Mostrar las parcelas disponibles --%>
<% if (parcelas != null && !parcelas.isEmpty()) { %>
<form method="POST" action="SolicitarTrabajoServlet" onsubmit="guardarSeccionActiva('sec_agricultor', 'agricultor_solicitudes')">
    <label for="parcela_id">Seleccionar Parcela:</label>
    <select name="parcela_id" id="parcela_id" required>
        <% for (Parcela parcela : parcelas) {%>
        <option value="<%= parcela.getNumParcela()%>">
            <%= parcela.getIdCatastro()%>
        </option>
        <% } %>
    </select><br>

    <label for="tipo_trabajo_agri_solicita">Seleccionar Tipo de Trabajo:</label>
    <select name="tipo_trabajo_agri_solicita" id="tipo_trabajo_agri_solicita" required>
        <% for (TipoTrabajo tipo : tiposTrabajo) {%>
        <option value="<%= tipo.getIdTipoTrabajo()%>">
            <%= tipo.getNombre()%>
        </option>
        <% } %>
    </select><br>

    <input type="submit" name="solicitar_trabajo" value="Solicitar Trabajo">
</form>
<% } else { %>
<p>No tienes parcelas disponibles para solicitar trabajo.</p>
<% } %>

<h2>Trabajos Solicitados Pendientes de Revisar</h2>

<%-- Mostrar los trabajos solicitados pendientes --%>
<% if (trabajosSolicitados != null && !trabajosSolicitados.isEmpty()) { %>
<table border="1">
    <tr>
        <th>ID Solicitud</th>
        <th>Parcela (ID Catastro)</th>
        <th>Tipo de Trabajo</th>
        <th>Fecha de Solicitud</th>
    </tr>
    <% for (TrabajoSolicitado trabajo : trabajosSolicitados) {%>
    <tr>
        <td><%= trabajo.getIdSolicitud()%></td>
        <td><%= trabajo.getNumParcela()%></td>
        <td>
            <%
                // Buscar el nombre del tipo de trabajo basado en el ID de tipoTrabajo
                String tipoTrabajoNombre = "";
                for (TipoTrabajo tipo : tiposTrabajo) {
                    if (tipo.getIdTipoTrabajo() == trabajo.getIdTipoTrabajo()) {
                        tipoTrabajoNombre = tipo.getNombre();
                        break;
                    }
                }
            %>
            <%= tipoTrabajoNombre%>
        </td>
        <td><%= trabajo.getFechaSolicitud()%></td>
    </tr>
    <% } %>
</table>
<% } else { %>
<p>No tienes trabajos solicitados pendientes de revisar.</p>
<% }%>
