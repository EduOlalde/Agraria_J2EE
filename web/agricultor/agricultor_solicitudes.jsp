<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.ecofield.modelos.Parcela" %>
<%@ page import="com.ecofield.modelos.TipoTrabajo" %>
<html>
<head>
    <title>Solicitar Trabajo</title>
</head>
<body>
    <h2>Solicitar Trabajo</h2>
    <h3>Selecciona tu Parcela y Tipo de Trabajo</h3>
    
    <!-- Mostrar mensaje de la sesión si existe -->
    <%
        String mensaje = (String) session.getAttribute("mensaje");
        if(mensaje != null) {
    %>
        <p><%= mensaje %></p>
    <%
            session.removeAttribute("mensaje");
        }
    %>
    
    <form action="SolicitarTrabajo" method="post">
        <label for="parcela_id">Seleccionar Parcela:</label>
        <select name="parcela_id" id="parcela_id" required>
            <%-- Se asume que en el controlador se envía la lista de parcelas del agricultor en el atributo "parcelas" --%>
            <%
                List<Parcela> parcelas = (List<Parcela>) request.getAttribute("parcelas");
                if(parcelas != null) {
                    for(Parcela p : parcelas) {
            %>
                        <option value="<%= p.getNumParcela() %>"><%= p.getIdCatastro() %></option>
            <%
                    }
                } else {
            %>
                <option value="">No tienes parcelas disponibles</option>
            <%
                }
            %>
        </select>
        <br>
        <label for="tipo_trabajo_agri_solicita">Seleccionar Tipo de Trabajo:</label>
        <select name="tipo_trabajo_agri_solicita" id="tipo_trabajo_agri_solicita" required>
            <%-- Se asume que se envía la lista de tipos de trabajo en el atributo "tiposTrabajo" --%>
            <%
                List<TipoTrabajo> tipos = (List<TipoTrabajo>) request.getAttribute("tiposTrabajo");
                if(tipos != null) {
                    for(TipoTrabajo t : tipos) {
            %>
                        <option value="<%= t.getIdTipoTrabajo() %>"><%= t.getNombre() %></option>
            <%
                    }
                } else {
            %>
                <option value="">No hay tipos de trabajo disponibles</option>
            <%
                }
            %>
        </select>
        <br>
        <input type="submit" value="Solicitar Trabajo">
    </form>
