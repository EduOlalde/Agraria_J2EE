<%@page import="java.util.ArrayList"%>
<%@page import="com.ecofield.modelos.TrabajoSolicitado"%>
<%@page import="com.ecofield.modelos.Usuario"%>
<%@page import="com.ecofield.modelos.Maquina"%>
<%@page import="com.ecofield.modelos.TipoTrabajo"%>
<%@page import="com.ecofield.modelos.Trabajo"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<TrabajoSolicitado> trabajosEnRevision = (List<TrabajoSolicitado>) request.getAttribute("trabajosEnRevision");
    List<TrabajoSolicitado> trabajosRechazados = (List<TrabajoSolicitado>) request.getAttribute("trabajosRechazados");
    List<Trabajo> trabajosPendientes = (List<Trabajo>) request.getAttribute("trabajosPendientes");
    List<Trabajo> trabajosEnCurso = (List<Trabajo>) request.getAttribute("trabajosEnCurso");
    List<Trabajo> trabajosFinalizados = (List<Trabajo>) request.getAttribute("trabajosFinalizados");
    List<TipoTrabajo> tiposTrabajo = (List<TipoTrabajo>) request.getAttribute("tiposTrabajo");
    List<Maquina> maquinas = (List<Maquina>) request.getAttribute("maquinas");
    List<Usuario> maquinistas = (List<Usuario>) request.getAttribute("maquinistas");
    String tipoTrabajoSeleccionado = request.getParameter("tipo_trabajo_agri_lista");
    String fechaInicio = request.getParameter("fecha_inicio");
    String fechaFin = request.getParameter("fecha_fin");
%>

<h2>Trabajos en Revisión</h2>
<% if (trabajosEnRevision != null && !trabajosEnRevision.isEmpty()) { %>
<table border="1">
    <thead>
        <tr>
            <th>Parcela</th>
            <th>Tipo de Trabajo</th>
            <th>Estado</th>
        </tr>
    </thead>
    <tbody>
        <%
            for (TrabajoSolicitado trabajo : trabajosEnRevision) {
                String nombreTipoTrabajo = "";
                for (TipoTrabajo tipo : tiposTrabajo) {
                    if (tipo.getIdTipoTrabajo() == trabajo.getIdTipoTrabajo()) {
                        nombreTipoTrabajo = tipo.getNombre();  // Nombre del tipo de trabajo
                        break;
                    }
                }
        %>
        <tr>
            <td><%= trabajo.getNumParcela()%></td>
            <td><%= nombreTipoTrabajo%></td>
            <td><%= trabajo.getEstado()%></td>
        </tr>
        <%
            }
        %>
    </tbody>
</table>
<% } else { %>
<p>No hay trabajos en revisión con los filtros seleccionados.</p>
<% } %>


<h2>Trabajos Aprobados</h2>
<form method="GET" action="dashboard" onsubmit="guardarSeccionActiva('sec_agricultor', 'agricultor_trabajos')">
    <div>
        <label for="tipo_trabajo_agri_lista">Filtrar por Tipo de Trabajo:</label>
        <select name="tipo_trabajo_agri_lista" id="tipo_trabajo_agri_lista">
            <option value="">Todos</option>
            <%
                if (tiposTrabajo != null) {
                    for (TipoTrabajo tipo : tiposTrabajo) {
                        String seleccionado = (tipoTrabajoSeleccionado != null && tipoTrabajoSeleccionado.equals(String.valueOf(tipo.getIdTipoTrabajo()))) ? "selected" : "";
            %>
            <option value="<%= tipo.getIdTipoTrabajo()%>" <%= seleccionado%>><%= tipo.getNombre()%></option>
            <%
                    }
                }
            %>
        </select>

        <label for="fecha_inicio">Fecha Inicio:</label>
        <input type="date" name="fecha_inicio" value="<%= fechaInicio%>">

        <label for="fecha_fin">Fecha Fin:</label>
        <input type="date" name="fecha_fin" value="<%= fechaFin%>">

        <button type="submit">Aplicar Filtros</button>
    </div>
</form>

<%-- Trabajos Pendientes --%>
<% if (trabajosPendientes != null && !trabajosPendientes.isEmpty()) { %>
<h3>Trabajos Pendientes</h3>
<table border="1">
    <thead>
        <tr>
            <th>Parcela</th>
            <th>Máquina</th>
            <th>Maquinista</th>
            <th>Tipo de Trabajo</th>
            <th>Estado</th>
        </tr>
    </thead>
    <tbody>
        <%
            for (Trabajo trabajo : trabajosPendientes) {
                int idMaquina = trabajo.getIdMaquina();
                int idMaquinista = trabajo.getIdMaquinista();

                String modeloMaquina = "";
                for (Maquina maquina : maquinas) {
                    if (maquina.getIdMaquina() == idMaquina) {
                        modeloMaquina = maquina.getModelo();
                        break;
                    }
                }

                String nombreMaquinista = "";
                for (Usuario maquinista : maquinistas) {
                    if (maquinista.getId() == idMaquinista) {
                        nombreMaquinista = maquinista.getNombre();
                        break;
                    }
                }
        %>
        <tr>
            <td><%= trabajo.getNumParcela()%></td>
            <td><%= modeloMaquina%></td>
            <td><%= nombreMaquinista%></td>
            <td>
                <%
                    String nombreTipoTrabajo = "";
                    for (TipoTrabajo tipo : tiposTrabajo) {
                        if (tipo.getIdTipoTrabajo() == trabajo.getTipoTrabajo()) {
                            nombreTipoTrabajo = tipo.getNombre();
                            break;
                        }
                    }
                %>
                <%= nombreTipoTrabajo%>
            </td>
            <td><%= trabajo.getEstado()%></td>
        </tr>
        <%
            }
        %>
    </tbody>
</table>
<% } else { %>
<p>No hay trabajos pendientes.</p>
<% } %>

<%-- Trabajos en Curso --%>
<% if (trabajosEnCurso != null && !trabajosEnCurso.isEmpty()) { %>
<h3>Trabajos En Curso</h3>
<table border="1">
    <thead>
        <tr>
            <th>Parcela</th>
            <th>Máquina</th>
            <th>Maquinista</th>
            <th>Tipo de Trabajo</th>
            <th>Estado</th>
            <th>Fecha Inicio</th>
        </tr>
    </thead>
    <tbody>
        <%
            for (Trabajo trabajo : trabajosEnCurso) {
                int idMaquina = trabajo.getIdMaquina();
                int idMaquinista = trabajo.getIdMaquinista();

                String modeloMaquina = "";
                for (Maquina maquina : maquinas) {
                    if (maquina.getIdMaquina() == idMaquina) {
                        modeloMaquina = maquina.getModelo();
                        break;
                    }
                }

                String nombreMaquinista = "";
                for (Usuario maquinista : maquinistas) {
                    if (maquinista.getId() == idMaquinista) {
                        nombreMaquinista = maquinista.getNombre();
                        break;
                    }
                }
        %>
        <tr>
            <td><%= trabajo.getNumParcela()%></td>
            <td><%= modeloMaquina%></td>
            <td><%= nombreMaquinista%></td>
            <td>
                <%
                    String nombreTipoTrabajo = "";
                    for (TipoTrabajo tipo : tiposTrabajo) {
                        if (tipo.getIdTipoTrabajo() == trabajo.getTipoTrabajo()) {
                            nombreTipoTrabajo = tipo.getNombre();
                            break;
                        }
                    }
                %>
                <%= nombreTipoTrabajo%>
            </td>
            <td><%= trabajo.getEstado()%></td>
            <td><%= trabajo.getFecInicio()%></td>
        </tr>
        <%
            }
        %>
    </tbody>
</table>
<% } else { %>
<p>No hay trabajos en curso.</p>
<% } %>

<%-- Trabajos Finalizados --%>
<% if (trabajosFinalizados != null && !trabajosFinalizados.isEmpty()) { %>
<h3>Trabajos Finalizados</h3>
<table border="1">
    <thead>
        <tr>
            <th>Parcela</th>
            <th>Máquina</th>
            <th>Maquinista</th>
            <th>Tipo de Trabajo</th>
            <th>Estado</th>
            <th>Fecha Inicio</th>
            <th>Fecha Fin</th>
            <th>Horas</th>
        </tr>
    </thead>
    <tbody>
        <%
            for (Trabajo trabajo : trabajosFinalizados) {
                int idMaquina = trabajo.getIdMaquina();
                int idMaquinista = trabajo.getIdMaquinista();

                String modeloMaquina = "";
                for (Maquina maquina : maquinas) {
                    if (maquina.getIdMaquina() == idMaquina) {
                        modeloMaquina = maquina.getModelo();
                        break;
                    }
                }

                String nombreMaquinista = "";
                for (Usuario maquinista : maquinistas) {
                    if (maquinista.getId() == idMaquinista) {
                        nombreMaquinista = maquinista.getNombre();
                        break;
                    }
                }
        %>
        <tr>
            <td><%= trabajo.getNumParcela()%></td>
            <td><%= modeloMaquina%></td>
            <td><%= nombreMaquinista%></td>
            <td>
                <%
                    String nombreTipoTrabajo = "";
                    for (TipoTrabajo tipo : tiposTrabajo) {
                        if (tipo.getIdTipoTrabajo() == trabajo.getTipoTrabajo()) {
                            nombreTipoTrabajo = tipo.getNombre();
                            break;
                        }
                    }
                %>
                <%= nombreTipoTrabajo%>
            </td>
            <td><%= trabajo.getEstado()%></td>
            <td><%= trabajo.getFecInicio()%></td>
            <td><%= trabajo.getFecFin()%></td>
            <td><%= trabajo.getHoras()%></td>
        </tr>
        <%
            }
        %>
    </tbody>
</table>
<% } else { %>
<p>No hay trabajos finalizados.</p>
<% } %>



<h2>Trabajos Rechazados</h2>
<% if (trabajosRechazados != null && !trabajosRechazados.isEmpty()) { %>
<table border="1">
    <thead>
        <tr>
            <th>Parcela</th>
            <th>Tipo de Trabajo</th>
            <th>Estado</th>
        </tr>
    </thead>
    <tbody>
        <%
            for (TrabajoSolicitado trabajo : trabajosRechazados) {
                String nombreTipoTrabajo = "";
                for (TipoTrabajo tipo : tiposTrabajo) {
                    if (tipo.getIdTipoTrabajo() == trabajo.getIdTipoTrabajo()) {
                        nombreTipoTrabajo = tipo.getNombre();  // Nombre del tipo de trabajo
                        break;
                    }
                }
        %>
        <tr>
            <td><%= trabajo.getNumParcela()%></td>
            <td><%= nombreTipoTrabajo%></td>
            <td><%= trabajo.getEstado()%></td>
        </tr>
        <%
            }
        %>
    </tbody>
</table>
<% } else { %>
<p>No hay trabajos rechazados con los filtros seleccionados.</p>
<% }%>
