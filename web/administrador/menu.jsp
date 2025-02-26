<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.ecofield.modelos.Rol" %>

<!-- Botones de navegación para el administrador -->
<button class="nav-button sub-section-btn" onclick="showSubSection('admin_usuarios')">Gestión de Usuarios</button>
<button class="nav-button sub-section-btn" onclick="showSubSection('admin_maquinistas')">Maquinistas</button>
<button class="nav-button sub-section-btn" onclick="showSubSection('admin_parcelas')">Parcelas</button>
<button class="nav-button sub-section-btn" onclick="showSubSection('admin_maquinas')">Máquinas</button>
<button class="nav-button sub-section-btn" onclick="showSubSection('admin_gestion_solicitudes')">Solicitudes de Trabajo</button>
<button class="nav-button sub-section-btn" onclick="showSubSection('admin_lista_trabajos')">Listar Trabajos</button>
<button class="nav-button sub-section-btn" onclick="showSubSection('admin_facturacion')">Facturación</button>

<!-- Sub-secciones para cada funcionalidad del administrador -->
<div id="admin_usuarios" class="sub-section-content">
    <jsp:include page="admin_usuarios.jsp" />
</div>

<div id="admin_maquinistas" class="sub-section-content">
    <jsp:include page="admin_maquinistas.jsp" />
</div>

<div id="admin_parcelas" class="sub-section-content">
    <jsp:include page="admin_parcelas.jsp" />
</div>

<div id="admin_maquinas" class="sub-section-content">
    <jsp:include page="admin_maquinas.jsp" />
</div>

<div id="admin_gestion_solicitudes" class="sub-section-content">
    <jsp:include page="admin_gestion_solicitudes.jsp" />
</div>

<div id="admin_lista_trabajos" class="sub-section-content">
    <jsp:include page="admin_lista_trabajos.jsp" />
</div>

<div id="admin_facturacion" class="sub-section-content">
    <jsp:include page="admin_facturacion.jsp" />
</div>
