<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, com.ecofield.modelos.Parcela, com.ecofield.dao.ParcelaDAO" %>
<%@ page import="com.mysql.jdbc.Connection" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%
    // Verificar que user_id no sea null antes de convertirlo a int
    Object userIdAttr = session.getAttribute("user_id");
    int idAgricultor = (userIdAttr != null) ? Integer.parseInt(userIdAttr.toString()) : 0;
    
    // Recuperar la conexión desde la sesión
    Connection conn = (Connection) session.getAttribute("conexion");
    if (conn == null) {
        out.println("No se ha establecido la conexión a la base de datos.");
        return;
    }
    
    // Crear el DAO y obtener las parcelas del agricultor
    ParcelaDAO parcelaDAO = new ParcelaDAO(conn);
    List<Parcela> parcelas = parcelaDAO.obtenerParcelasDeAgricultor(idAgricultor);
%>

<h2>Mis Parcelas</h2>

<% if (parcelas != null && !parcelas.isEmpty()) { %>
    <table border="1">
        <tr>
            <th>Número de Parcela</th>
            <th>ID Catastro</th>
            <th>Extensión</th>
            <th>Propietario</th>
        </tr>
        <% for (Parcela parcela : parcelas) { %>
            <tr>
                <td><%= parcela.getNumParcela() %></td>
                <td><%= parcela.getIdCatastro() %></td>
                <td><%= parcela.getExtension() %></td>
                <td><%= parcela.getPropietario() %></td>
            </tr>
        <% } %>
    </table>
<% } else { %>
    <p>No se encontraron parcelas.</p>
<% } %>
