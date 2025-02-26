<%-- 
    Document   : menu
    Created on : 25 feb 2025, 21:20:30
    Author     : Eduardo Olalde
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.ecofield.modelos.Rol" %>

<button class="nav-button" onclick="showSubSection('maquinista_trabajos')">Mis Trabajos</button>

<!-- Sub-sección de Maquinista -->
<div id="maquinista_trabajos" class="sub-section-content">
    <jsp:include page="maq_trabajos.jsp" />
</div>
