<%-- 
    Document   : agricultor_facturas
    Created on : 26 feb 2025, 18:38:53
    Author     : diego
--%>

<%@page import="com.ecofield.modelos.Factura"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Factura> facturasPendientes = (List<Factura>) request.getAttribute("facturasPendientes");
    List<Factura> facturasPagadas = (List<Factura>) request.getAttribute("facturasPagadas");
%>

<h2>Facturas Pendientes de Pago</h2>
<% if (facturasPendientes != null && !facturasPendientes.isEmpty()) { %>
<table border="1">
    <tr>
        <th>ID Factura</th>
        <th>ID Trabajo</th>
        <th>Fecha Emisión</th>
        <th>Monto</th>
        <th>Acción</th>
    </tr>
    <% for (Factura factura : facturasPendientes) {%>
    <tr>
        <td><%= factura.getIdFactura()%></td>
        <td><%= factura.getIdTrabajo()%></td>
        <td><%= factura.getFechaEmision()%></td>
        <td><%= String.format("%.2f", factura.getMonto())%> €</td>
        <td>
            <form method="post" action="AgricultorFacturasServlet" onsubmit="guardarSeccionActiva('sec_agricultor', 'agricultor_facturas')">
                <input type="hidden" name="id_factura" value="<%= factura.getIdFactura()%>">
                <button type="submit" name="marcar_pagada">Marcar como Pagada</button>
            </form>
        </td>
    </tr>
    <% } %>
</table>
<% } else { %>
<p>No hay facturas pendientes de pago.</p>
<% } %>

<h2>Historial de Facturas Pagadas</h2>
<% if (facturasPagadas != null && !facturasPagadas.isEmpty()) { %>
<table border="1">
    <tr>
        <th>ID Factura</th>
        <th>ID Trabajo</th>
        <th>Fecha Emisión</th>
        <th>Fecha Pago</th>
        <th>Monto</th>
    </tr>
    <% for (Factura factura : facturasPagadas) {%>
    <tr>
        <td><%= factura.getIdFactura()%></td>
        <td><%= factura.getIdTrabajo()%></td>
        <td><%= factura.getFechaEmision()%></td>
        <td><%= (factura.getFechaPago() != null) ? factura.getFechaPago() : "Pendiente"%></td>
        <td><%= String.format("%.2f", factura.getMonto())%> €</td>
    </tr>
    <% } %>
</table>
<% } else { %>
<p>No hay facturas pagadas registradas.</p>
<% }%>
