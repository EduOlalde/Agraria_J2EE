/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ecofield.controlUsuarios;

import com.ecofield.dao.*;
import com.ecofield.modelos.*;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Eduardo Olalde
 */
public class DashboardServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);
        Connection conn = (Connection) session.getAttribute("conexion");

        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        int idUsuario = usuario.getId();

        if (!usuario.isHabilitado()) {
            session.setAttribute("mensaje", "Tu cuenta está deshabilitada. Contacta con el administrador.");
            response.sendRedirect("dashboard.jsp");
            return;
        }

        Rol rolAdmin = new Rol(1, "Administrador");
        Rol rolAgricultor = new Rol(2, "Agricultor");
        Rol rolMaquinista = new Rol(3, "Maquinista");

        List<Rol> roles = usuario.getRoles();

        if (roles.contains(rolAdmin)) {
            //Instancaición de todos los DAO necesarios
            RolDAO rolDAO = new RolDAO(conn);
            UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
            MaquinistaDAO maquinistaDAO = new MaquinistaDAO(conn);
            ParcelaDAO parcelaDAO = new ParcelaDAO(conn);
            MaquinaDAO maquinaDAO = new MaquinaDAO(conn);
            TipoTrabajoDAO tipoTrabajoDAO = new TipoTrabajoDAO(conn);
            TrabajoSolicitadoDAO trabajoSolicitadoDAO = new TrabajoSolicitadoDAO(conn);
            TrabajoDAO trabajoDAO = new TrabajoDAO(conn);
            FacturaDAO facturaDAO = new FacturaDAO(conn);

            /* Módulo gestión usuarios */
            List<Rol> rolesDisponibles = rolDAO.obtenerRolesDisponibles();
            request.setAttribute("rolesDisponibles", rolesDisponibles);
            List<Usuario> usuarios = usuarioDAO.listarUsuarios();
            request.setAttribute("usuarios", usuarios);

            /* Módulo gestión maquinistas */
            List<Maquinista> maquinistas = maquinistaDAO.obtenerMaquinistas();
            List<TipoTrabajo> tiposTrabajo = tipoTrabajoDAO.obtenerTiposTrabajo();
            request.setAttribute("adminMaquinistas", maquinistas);
            request.setAttribute("tiposTrabajo", tiposTrabajo);

            /* Módulo gestión parcelas */
            List<Usuario> agricultores = usuarioDAO.obtenerUsuariosPorRol("Agricultor");
            // Obtener los filtros
            // Obtener los parámetros como cadenas
            String agricultorParam = request.getParameter("agricultor");
            String extensionParam = request.getParameter("extension");
            // Validar antes de convertir
            Integer agricultorFiltro = (agricultorParam != null && !agricultorParam.trim().isEmpty()) ? Integer.valueOf(agricultorParam) : null;
            Double extensionFiltro = (extensionParam != null && !extensionParam.trim().isEmpty()) ? Double.valueOf(extensionParam) : null;

            // Obtener las parcelas filtradas           
            List<Parcela> parcelas = parcelaDAO.obtenerParcelas(agricultorFiltro, extensionFiltro);

            // Atributos para la vista
            request.setAttribute("agricultores", agricultores);
            request.setAttribute("parcelas", parcelas);
            request.setAttribute("filtroAgricultor", agricultorFiltro);
            request.setAttribute("filtroExtension", extensionFiltro);

            /* Módulo gestión máquinas  */
            String paramEstado = request.getParameter("estado_filtro");
            String paramTipoMaquina = request.getParameter("tipo_maquina_filtro");

            Integer filtroTipoMaquina = (paramTipoMaquina != null && !paramTipoMaquina.trim().isEmpty()) ? Integer.valueOf(paramTipoMaquina) : null;
            String filtroEstado = (paramEstado != null && !paramEstado.trim().isEmpty()) ? paramEstado : null;

            List<Maquina> maquinas = maquinaDAO.obtenerMaquinas(filtroEstado, filtroTipoMaquina);
            request.setAttribute("maquinas", maquinas);

            // Obtener tipos de trabajo para el filtro
            request.setAttribute("tiposTrabajo", tipoTrabajoDAO.obtenerTiposTrabajo());

            /* Módulo gestión solicitudes */
            List<TrabajoSolicitado> trabajosSolicitados = trabajoSolicitadoDAO.obtenerTrabajosSolicitadosPorEstado("En revision");
            request.setAttribute("trabajosSolicitados", trabajosSolicitados);

            List<Maquina> maquinasDisponibles = maquinaDAO.getMaquinasDisponibles();
            request.setAttribute("maquinasDisponibles", maquinasDisponibles);

            /* Módulo listar trabajos */
            // Obtener filtros
            String paramAgricultor = request.getParameter("agricultor");
            Integer filtroAgricultor = (paramAgricultor != null && !paramAgricultor.trim().isEmpty()) ? Integer.valueOf(paramAgricultor) : null;

            String paramTipoTrabajo = request.getParameter("listaTrabajostipoTrabajo");
            Integer filtroTipoTrabajo = (paramTipoTrabajo != null && !paramTipoTrabajo.trim().isEmpty()) ? Integer.valueOf(paramTipoTrabajo) : null;

            String paramOrdenFecha = request.getParameter("listaTrabajosOrden");
            String ordenFecha = (paramOrdenFecha != null && paramOrdenFecha.equals("asc")) ? "ASC" : "DESC";

            // Obtener lista de trabajos filtrados
            List<Trabajo> listaTrabajos = trabajoDAO.obtenerTrabajosFiltrados(filtroAgricultor, filtroTipoTrabajo, ordenFecha);
            request.setAttribute("listaTrabajos", listaTrabajos);

            /* Módulo admin facturas */
            // Obtener facturas pendientes de generar
            List<Factura> facturasPendientes = facturaDAO.obtenerFacturasPendientes();
            request.setAttribute("facturasPendientes", facturasPendientes);

            // Obtener historial de facturas
            List<Factura> historialFacturas = facturaDAO.obtenerHistorialFacturas(null);
            request.setAttribute("historialFacturas", historialFacturas);

        }

        if (roles.contains(rolAgricultor)) {

            ParcelaDAO parcelaDAO = new ParcelaDAO(conn);
            FacturaDAO facturaDAO = new FacturaDAO(conn);
            TrabajoDAO trabajoDAO = new TrabajoDAO(conn);
            MaquinaDAO maquinaDAO = new MaquinaDAO(conn);
            UsuarioDAO usuarioDAO = new UsuarioDAO(conn);

            TipoTrabajoDAO tipoTrabajoDAO = new TipoTrabajoDAO(conn);
            TrabajoSolicitadoDAO trabajoSolicitadoDAO = new TrabajoSolicitadoDAO(conn);

            int idAgricultor = (int) session.getAttribute("user_id");

            /* Módulo listado de parcelas */
            List<Parcela> parcelas = parcelaDAO.obtenerParcelasDeAgricultor(idUsuario);
            request.setAttribute("parcelasAgricultor", parcelas);

            /* Módulo solicitud de trabajos */
            // Obtener tipos de trabajo para el filtro
            request.setAttribute("tiposTrabajo", tipoTrabajoDAO.obtenerTiposTrabajo());
            List<TrabajoSolicitado> trabajosSolicitados = trabajoSolicitadoDAO.obtenerTrabajosSolicitadosPorPropietarioYEstado(idAgricultor, "En revision");
            request.setAttribute("trabajosSolicitados", trabajosSolicitados);

            /* Módulo listado de trabajos */
            // Obtener filtros
            String paramTipoTrabajo = request.getParameter("tipo_trabajo_agri_lista");
            Integer filtroTipoTrabajo = (paramTipoTrabajo != null && !paramTipoTrabajo.trim().isEmpty()) ? Integer.valueOf(paramTipoTrabajo) : null;

            String filtroFechaInicio = request.getParameter("fecha_inicio");
            filtroFechaInicio = (filtroFechaInicio != null && !filtroFechaInicio.trim().isEmpty()) ? filtroFechaInicio : null;

            String filtroFechaFin = request.getParameter("fecha_fin");
            filtroFechaFin = (filtroFechaFin != null && !filtroFechaFin.trim().isEmpty()) ? filtroFechaFin : null;

            // Cargar máquinas y maquinistas para mostrar sus datos
            List<Maquina> maquinas = maquinaDAO.obtenerMaquinas(null, null);
            request.setAttribute("maquinas", maquinas);
            List<Usuario> maquinistas = usuarioDAO.obtenerUsuariosPorRol("Maquinista");
            request.setAttribute("maquinistas", maquinistas);

            List<Trabajo> trabajosPendientes = trabajoDAO.obtenerTrabajos(idAgricultor, filtroTipoTrabajo, "Pendiente", null, null, null);
            List<Trabajo> trabajosEnCurso = trabajoDAO.obtenerTrabajos(idAgricultor, filtroTipoTrabajo, "En curso", null, filtroFechaInicio, null);
            List<Trabajo> trabajosFinalizados = trabajoDAO.obtenerTrabajos(idAgricultor, filtroTipoTrabajo, "Finalizado", null, filtroFechaInicio, filtroFechaFin);

            // Cargar los trabajos rechazados y en revisión si es necesario (puedes adaptar este código según sea necesario)
            List<TrabajoSolicitado> trabajosRechazados = trabajoSolicitadoDAO.obtenerTrabajosSolicitadosPorPropietarioYEstado(idAgricultor, "Rechazado");
            List<TrabajoSolicitado> trabajosEnRevision = trabajoSolicitadoDAO.obtenerTrabajosSolicitadosPorPropietarioYEstado(idAgricultor, "En Revisión");

            // Agregar las listas al request para que puedan ser usadas en la vista JSP
            request.setAttribute("trabajosPendientes", trabajosPendientes);
            request.setAttribute("trabajosEnCurso", trabajosEnCurso);
            request.setAttribute("trabajosFinalizados", trabajosFinalizados);
            request.setAttribute("trabajosRechazados", trabajosRechazados);
            request.setAttribute("trabajosEnRevision", trabajosEnRevision);

            /* Módulo facturación */
            List<Factura> facturasPendientes = facturaDAO.obtenerFacturasPendientesPago(idAgricultor);
            List<Factura> facturasPagadas = facturaDAO.obtenerFacturasPagadas(idAgricultor);
            request.setAttribute("facturasPendientes", facturasPendientes);
            request.setAttribute("facturasPagadas", facturasPagadas);

        }

        if (roles.contains(rolMaquinista)) {
            TrabajoDAO trabajoDAO = new TrabajoDAO(conn);
            int idMaquinista = (int) session.getAttribute("user_id");
            // Obtener trabajos pendientes
            List<Trabajo> trabajosMaquinistaPendientes = trabajoDAO.obtenerTrabajosPendientes(idMaquinista);
            request.setAttribute("trabajosMaquinistaPendientes", trabajosMaquinistaPendientes);

            // Obtener trabajos en curso
            List<Trabajo> trabajosMaquinistaEnCurso = trabajoDAO.obtenerTrabajosEnCurso(idMaquinista);
            request.setAttribute("trabajosMaquinistaEnCurso", trabajosMaquinistaEnCurso);

            // Obtener historial de trabajos finalizados
            List<Trabajo> historialMaquinistaTrabajos = trabajoDAO.obtenerHistorialTrabajos(idMaquinista);
            request.setAttribute("historialMaquinistaTrabajos", historialMaquinistaTrabajos);

        }

        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
