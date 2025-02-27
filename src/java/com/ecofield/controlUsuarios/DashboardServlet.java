/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ecofield.controlUsuarios;

import com.ecofield.dao.MaquinaDAO;
import com.ecofield.dao.MaquinistaDAO;
import com.ecofield.dao.ParcelaDAO;
import com.ecofield.dao.RolDAO;
import com.ecofield.dao.TipoTrabajoDAO;
import com.ecofield.dao.UsuarioDAO;
import com.ecofield.modelos.Maquina;
import com.ecofield.modelos.Maquinista;
import com.ecofield.modelos.Parcela;
import com.ecofield.modelos.Rol;
import com.ecofield.modelos.TipoTrabajo;
import com.ecofield.modelos.Usuario;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.io.PrintWriter;
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

            // Módulo gestión usuarios
            List<Rol> rolesDisponibles = rolDAO.obtenerRolesDisponibles();
            request.setAttribute("rolesDisponibles", rolesDisponibles);
            List<Usuario> usuarios = usuarioDAO.listarUsuarios();
            request.setAttribute("usuarios", usuarios);

            // Módulo gestión maquinistas
            List<Maquinista> maquinistas = maquinistaDAO.obtenerMaquinistas();
            List<TipoTrabajo> tiposTrabajo = tipoTrabajoDAO.obtenerTiposTrabajo();
            request.setAttribute("maquinistas", maquinistas);
            request.setAttribute("tiposTrabajo", tiposTrabajo);

            // Módulo gestión parcelas
            List<Usuario> agricultores = usuarioDAO.obtenerAgricultores();
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

            // Módulo gestión máquinas
            String paramEstado = request.getParameter("estado_filtro");
            String paramTipoMaquina = request.getParameter("tipo_maquina_filtro");
            
            Integer filtroTipoMaquina = (paramTipoMaquina != null && !paramTipoMaquina.trim().isEmpty()) ? Integer.valueOf(paramTipoMaquina) : null;
            String filtroEstado = (paramEstado != null && !paramEstado.trim().isEmpty()) ? paramEstado : null;
            
            List<Maquina> maquinas = maquinaDAO.obtenerMaquinas(filtroEstado, filtroTipoMaquina);
            request.setAttribute("maquinas", maquinas);

            // Obtener tipos de trabajo para el filtro
            request.setAttribute("tiposTrabajo", new TipoTrabajoDAO(conn).obtenerTiposTrabajo());


        }

        if (roles.contains(rolAgricultor)) {

            ParcelaDAO parcelaDAO = new ParcelaDAO(conn);
            List<Parcela> parcelas = parcelaDAO.obtenerParcelasDeAgricultor(idUsuario);
            request.setAttribute("parcelasAgricultor", parcelas);

        }

        if (roles.contains(rolMaquinista)) {

        }

        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
        /*
            try (PrintWriter out = response.getWriter()) {
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NewServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Parcelas " + parcelas + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }*/
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
