<%@page import="com.ecofield.modelos.Parcela"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    List<Parcela> parcelas = (List<Parcela>)request.getAttribute("parcelasAgricultor");
%>

<h2>Mis Parcelas</h2>

<% if (parcelas != null && !parcelas.isEmpty()) { %>
    <table border="1">
        <tr>
            <th>Número de Parcela</th>
            <th>ID Catastro</th>
            <th>Extensión</th>
        </tr>
        <% for (Parcela parcela : parcelas) { %>
            <tr>
                <td><%= parcela.getNumParcela() %></td>
                <td><%= parcela.getIdCatastro() %></td>
                <td><%= parcela.getExtension() %></td>
            </tr>
        <% } %>
    </table>
<% } else { %>
    <p>No se encontraron parcelas.</p>
<% } %>
