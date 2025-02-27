<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.ecofield.modelos.Rol" %>

<!-- Botones de navegación para el agricultor -->
<button class="nav-button" onclick="showSubSection('agricultor_parcelas')">Mis Parcelas</button>
<button class="nav-button" onclick="showSubSection('agricultor_solicitudes')">Solicitudes de Trabajo</button>
<button class="nav-button" onclick="showSubSection('agricultor_trabajos')">Listar Trabajos</button>
<button class="nav-button" onclick="showSubSection('agricultor_facturas')">Facturación</button>

<!-- Sub-secciones para cada funcionalidad del agricultor -->
<div id="agricultor_parcelas" class="sub-section-content">
    <jsp:include page="agricultor_parcelas.jsp" />
</div>

<div id="agricultor_solicitudes" class="sub-section-content">
    <jsp:include page="agricultor_solicitudes.jsp" />
</div>

<div id="agricultor_trabajos" class="sub-section-content">
    <jsp:include page="agricultor_trabajos.jsp" />
</div>

<div id="agricultor_facturas" class="sub-section-content">
    <jsp:include page="agricultor_facturas.jsp" />
</div>