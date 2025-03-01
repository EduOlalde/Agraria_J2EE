<%-- 
    Document   : admin_gestion_solicitudes
    Created on : 26 feb 2025, 18:43:33
    Author     : diego
--%>


<%@page import="com.ecofield.modelos.Usuario"%>
<%@page import="com.ecofield.modelos.TipoTrabajo"%>
<%@page import="com.ecofield.modelos.Maquina"%>
<%@page import="com.ecofield.modelos.Maquinista"%>
<%@page import="com.ecofield.modelos.TrabajoSolicitado"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Obtener las solicitudes de trabajo, los maquinistas disponibles y las máquinas desde la request
    List<TrabajoSolicitado> trabajosSolicitados = (List<TrabajoSolicitado>) request.getAttribute("trabajosSolicitados");
    List<Maquinista> maquinistas = (List<Maquinista>) request.getAttribute("maquinistas");
    String filtroTrabajo = request.getParameter("tipoTrabajo");
    List<Maquina> maquinasDisponibles = (List<Maquina>) request.getAttribute("maquinasDisponibles");
    List<TipoTrabajo> tiposTrabajo = (List<TipoTrabajo>) request.getAttribute("tiposTrabajo");
    List<Usuario> agricultores = (List<Usuario>) request.getAttribute("agricultores");
%>

<h3>Gestión de Trabajos Solicitados</h3>
<%-- Mostrar los trabajos solicitados --%>
<h3>Trabajos Pendientes de Asignación</h3>
<% if (trabajosSolicitados != null && !trabajosSolicitados.isEmpty()) { %>
<table border="1">
    <tr>
        <th>ID Solicitud</th>
        <th>Parcela</th>
        <th>Propietario</th>
        <th>Tipo de Trabajo</th>
        <th>Estado</th>
        <th>Acción</th>
    </tr>
    <% for (TrabajoSolicitado trabajo : trabajosSolicitados) {%>
    <tr>
    <form method="POST" action="TrabajosSolicitadosServlet" onsubmit="guardarSeccionActiva('sec_administrador', 'admin_gestion_solicitudes')">
        <input type="hidden" name="id_solicitud" value="<%= trabajo.getIdSolicitud()%>">
        <td><%= trabajo.getIdSolicitud()%></td>
        <td><%= trabajo.getNumParcela()%></td>
        <td>
            <%
                // Obtener el nombre del propietario cruzando el ID con la lista de agricultores
                String nombrePropietario = "";
                for (Usuario agricultor : agricultores) {
                    if (agricultor.getId() == trabajo.getPropietario()) {
                        nombrePropietario = agricultor.getNombre();  // Nombre del propietario
                        break;
                    }
                }
            %>
            <%= nombrePropietario%>
        </td>

        <td>
            <%
                // Obtener el nombre del tipo de trabajo cruzando el ID con la lista de tiposTrabajo
                String nombreTipoTrabajo = "";
                for (TipoTrabajo tipo : tiposTrabajo) {
                    if (tipo.getIdTipoTrabajo() == trabajo.getIdTipoTrabajo()) {
                        nombreTipoTrabajo = tipo.getNombre();  // Nombre del tipo de trabajo
                        break;
                    }
                }
            %>
            <%= nombreTipoTrabajo%>
        </td>
        <td><%= trabajo.getEstado()%></td>
        <td>
            <label for="maquinista">Maquinista:</label>
            <select name="maquinista">
                <%
                    boolean maquinistasDisponibles = false;
                    // Filtrar maquinistas disponibles por especialidades
                    for (Maquinista maquinista : maquinistas) {
                        // Filtrar por especialidad del maquinista y tipo de trabajo de la solicitud
                        if (maquinista.getEspecialidades().contains(trabajo.getIdTipoTrabajo())) {
                            maquinistasDisponibles = true;
                %>
                <option value="<%= maquinista.getId()%>"><%= maquinista.getNombre()%></option>
                <%
                        }
                    }
                    if (!maquinistasDisponibles) {
                %>
                <option disabled>No hay maquinistas disponibles para este trabajo</option>
                <%
                    }
                %>
            </select>

            <label for="maquina">Máquina:</label>
            <select name="maquina">
                <%
                    boolean maquinasDisponiblesFlag = false;
                    // Filtrar las máquinas disponibles basadas en el tipo de trabajo de la solicitud
                    for (Maquina maquina : maquinasDisponibles) {
                        if (maquina.getTipoMaquina() == (trabajo.getIdTipoTrabajo()) && maquina.getEstado().equals("Disponible")) {
                            maquinasDisponiblesFlag = true;
                %>
                <option value="<%= maquina.getIdMaquina()%>">
                    <%
                        // Obtener el nombre del tipo de máquina cruzando el ID de la máquina con tiposTrabajo
                        String nombreTipoMaquina = "";
                        for (TipoTrabajo tipo : tiposTrabajo) {
                            if (tipo.getIdTipoTrabajo() == maquina.getTipoMaquina()) {
                                nombreTipoMaquina = tipo.getNombre();  // Nombre del tipo de trabajo
                                break;
                            }
                        }
                    %>
                    <%= nombreTipoMaquina%> - Modelo: <%= maquina.getModelo()%>
                </option>
                <%
                        }
                    }
                    if (!maquinasDisponiblesFlag) {
                %>
                <option disabled>No hay máquinas disponibles para este trabajo</option>
                <%
                    }
                %>
            </select>

            <input type="submit" name="asignar_trabajo" value="Asignar">
            <input type="submit" name="rechazar_trabajo" value="Rechazar">
        </td>
    </form>
</tr>
<% } %>
</table>
<% } else { %>
<p>No hay trabajos pendientes de asignación.</p>
<% }%>