/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ecofield.controlUsuarios;

import com.ecofield.dao.MaquinistaDAO;
import com.ecofield.dao.ParcelaDAO;
import com.ecofield.dao.RolDAO;
import com.ecofield.dao.UsuarioDAO;
import com.ecofield.modelos.Maquinista;
import com.ecofield.modelos.Parcela;
import com.ecofield.modelos.Rol;
import com.ecofield.modelos.TipoTrabajo;
import com.ecofield.modelos.Usuario;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

            RolDAO rolDAO = new RolDAO(conn);
            List<Rol> rolesDisponibles = rolDAO.obtenerRolesDisponibles();
            request.setAttribute("rolesDisponibles", rolesDisponibles);

            UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
            List<Usuario> usuarios = usuarioDAO.listarUsuarios();
            request.setAttribute("usuarios", usuarios);

            MaquinistaDAO maquinistaDAO = new MaquinistaDAO(conn);

            List<Maquinista> maquinistas = maquinistaDAO.obtenerMaquinistas();
            List<TipoTrabajo> tiposTrabajo = maquinistaDAO.obtenerTiposTrabajo();

            request.setAttribute("maquinistas", maquinistas);
            request.setAttribute("tiposTrabajo", tiposTrabajo);

        }

        if (roles.contains(rolAgricultor)) {
            ParcelaDAO parcelaDAO = new ParcelaDAO(conn);
            try {
                List<Parcela> parcelas = parcelaDAO.obtenerParcelasDeAgricultor(idUsuario);
                request.setAttribute("parcelasAgricultor", parcelas);
            } catch (SQLException ex) {
                Logger.getLogger(DashboardServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        if (roles.contains(rolMaquinista)) {

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
