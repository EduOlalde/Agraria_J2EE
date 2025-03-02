<%-- 
    Document   : admin_facturacion
    Created on : 26 feb 2025, 18:43:52
    Author     : diego
--%>

<%@page import="java.util.List"%>
<%@page import="com.ecofield.modelos.Factura"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Factura> facturasPendientesDeGenerar = (List<Factura>) request.getAttribute("adminFacturasPendientes");

    List<Factura> historialFacturas = (List<Factura>) request.getAttribute("adminHistorialFacturas");
%>
<h3>Facturas Pendientes de Generar</h3>
<% if (facturasPendientesDeGenerar != null && !facturasPendientesDeGenerar.isEmpty()) { %>
<table border="1">
    <tr>
        <th>ID Factura</th>
        <th>ID Trabajo</th>
        <th>Estado</th>
        <th>Monto</th>
        <th>Generar Factura</th>
    </tr>
    <% for (Factura factura : facturasPendientesDeGenerar) {%>
    <tr>
        <td><%= factura.getIdFactura()%></td>
        <td><%= factura.getIdTrabajo()%></td>
        <td><%= factura.getEstado()%></td>
        <td><%= String.format("%.2f", factura.getMonto())%> €</td>
        <td>
            <form method="post" action="AdminFacturaServlet" onsubmit="guardarSeccionActiva('sec_administrador', 'admin_facturacion')">
                <input type="hidden" name="id_factura" value="<%= factura.getIdFactura()%>">
                <button type="submit" name="generar_factura">Generar</button>
            </form>
        </td>
    </tr>
    <% } %>
</table>
<% } else { %>
<p>No hay facturas pendientes de generar.</p>
<% } %>

<h3>Historial de Facturas</h3>
<% if (historialFacturas != null && !historialFacturas.isEmpty()) { %>
<table border="1">
    <tr>
        <th>ID Factura</th>
        <th>ID Trabajo</th>
        <th>Estado</th>
        <th>Fecha Emisión</th>
        <th>Fecha Pago</th>
        <th>Monto</th>
    </tr>
    <% for (Factura factura : historialFacturas) {%>
    <tr>
        <td><%= factura.getIdFactura()%></td>
        <td><%= factura.getIdTrabajo()%></td>
        <td><%= factura.getEstado()%></td>
        <td><%= factura.getFechaEmision()%></td>
        <td><%= (factura.getFechaPago() != null) ? factura.getFechaPago() : "Pendiente"%></td>
        <td><%= String.format("%.2f", factura.getMonto())%> €</td>
    </tr>
    <% } %>
</table>
<% } else { %>
<p>No hay facturas registradas en el sistema.</p>
<% }%>
