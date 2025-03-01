/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ecofield.controlAdmin;

import com.ecofield.dao.TrabajoSolicitadoDAO;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Eduardo Olalde
 */
public class TrabajosSolicitadosServlet extends HttpServlet {

    private void asignarTrabajo(HttpServletRequest request, HttpSession session, TrabajoSolicitadoDAO trabajoDAO) {
        try {
            int idSolicitud = Integer.parseInt(request.getParameter("id_solicitud"));
            String idMaquinistaStr = request.getParameter("maquinista");
            String idMaquinaStr = request.getParameter("maquina");

            if (idMaquinistaStr == null || idMaquinistaStr.isEmpty() || idMaquinaStr == null || idMaquinaStr.isEmpty()) {
                session.setAttribute("error", "Debes seleccionar un maquinista y una máquina para asignar el trabajo.");
                return;
            }

            int idMaquinista = Integer.parseInt(idMaquinistaStr);
            int idMaquina = Integer.parseInt(idMaquinaStr);

            boolean asignado = trabajoDAO.asignarTrabajo(idSolicitud, idMaquinista, idMaquina);

            if (asignado) {
                session.setAttribute("mensaje", "Trabajo asignado correctamente.");
            } else {
                session.setAttribute("error", "Error al asignar el trabajo.");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Datos inválidos en la solicitud.");
        }
    }

    private void rechazarTrabajo(HttpServletRequest request, HttpSession session, TrabajoSolicitadoDAO trabajoDAO) {
        try {
            int idSolicitud = Integer.parseInt(request.getParameter("id_solicitud"));
            boolean rechazado = trabajoDAO.rechazarTrabajo(idSolicitud);

            if (rechazado) {
                session.setAttribute("mensaje", "Trabajo rechazado correctamente.");
            } else {
                session.setAttribute("error", "Error al rechazar el trabajo.");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Error al procesar la solicitud de rechazo.");
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
        Connection conn = (Connection) request.getSession().getAttribute("conexion");
        TrabajoSolicitadoDAO trabajoDAO = new TrabajoSolicitadoDAO(conn);

        if (request.getParameter("asignar_trabajo") != null) {
            asignarTrabajo(request, session, trabajoDAO);
        } else if (request.getParameter("rechazar_trabajo") != null) {
            rechazarTrabajo(request, session, trabajoDAO);
        }

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
