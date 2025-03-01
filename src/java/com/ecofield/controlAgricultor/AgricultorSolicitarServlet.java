/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ecofield.controlAgricultor;

import com.ecofield.dao.TrabajoSolicitadoDAO;
import com.ecofield.modelos.TrabajoSolicitado;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Eduardo Olalde
 */
public class AgricultorSolicitarServlet extends HttpServlet {

    private void solicitarTrabajo(HttpServletRequest request, HttpSession session, TrabajoSolicitadoDAO trabajoDAO) {

        // Obtener los parámetros del formulario
        int numParcela = Integer.parseInt(request.getParameter("parcela_id"));
        int idTipoTrabajo = Integer.parseInt(request.getParameter("tipo_trabajo_agri_solicita"));
        int propietario = Integer.parseInt(request.getParameter("user_id"));

        TrabajoSolicitado solicitud = new TrabajoSolicitado(numParcela, propietario, idTipoTrabajo);

        // Insertar la solicitud de trabajo en la BD
        boolean exito = trabajoDAO.crearSolicitud(solicitud);

        if (exito) {
            session.setAttribute("mensaje", "Solicitud de trabajo realizada correctamente.");
        } else {
            session.setAttribute("error", "Error al realizar la solicitud de trabajo.");
        }
    }

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
        TrabajoSolicitadoDAO trabajoDAO = new TrabajoSolicitadoDAO(conn);

        solicitarTrabajo(request, session, trabajoDAO);

        response.sendRedirect("dashboard");
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
