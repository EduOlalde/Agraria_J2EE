<%-- 
    Document   : menu
    Created on : 25 feb 2025, 21:20:05
    Author     : Eduardo Olalde
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<button class="nav-button sub-section-btn" onclick="showSubSection('admin_usuarios')">Gestión de Usuarios</button>
<button class="nav-button sub-section-btn" onclick="showSubSection('admin_maquinistas')">Maquinistas</button>
<button class="nav-button sub-section-btn" onclick="showSubSection('admin_parcelas')">Parcelas</button>
<button class="nav-button sub-section-btn" onclick="showSubSection('admin_maquinas')">Máquinas</button>
<button class="nav-button sub-section-btn" onclick="showSubSection('admin_gestion_solicitudes')">Solicitudes de Trabajo</button>
<button class="nav-button sub-section-btn" onclick="showSubSection('admin_lista_trabajos')">Listar Trabajos</button>
<button class="nav-button sub-section-btn" onclick="showSubSection('admin_facturacion')">Facturación</button>

<!-- Sub-secciones de Administrador -->
<div id="admin_usuarios" class="sub-section-content" style="display: none;">
    <jsp:include page="admin_usuarios.jsp" />
</div>

