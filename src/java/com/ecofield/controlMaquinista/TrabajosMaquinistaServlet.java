/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ecofield.controlMaquinista;

import com.ecofield.dao.TrabajoDAO;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Eduardo Olalde
 */
public class TrabajosMaquinistaServlet extends HttpServlet {

    private void iniciarTrabajo(HttpServletRequest request, HttpSession session, TrabajoDAO trabajoDAO) {
        try {
            String fechaInicioString = request.getParameter("fecha_inicio");

            if (fechaInicioString == null || fechaInicioString.trim().isEmpty()) {
                session.setAttribute("error", "Debe ingresar una fecha de inicio.");
                return;
            }

            int idTrabajo = Integer.parseInt(request.getParameter("id_trabajo"));
            Date fechaInicio = Date.valueOf(fechaInicioString);

            boolean trabajoIniciado = trabajoDAO.iniciarTrabajo(idTrabajo, fechaInicio);

            if (trabajoIniciado) {
                session.setAttribute("mensaje", "Trabajo iniciado correctamente.");
            } else {
                session.setAttribute("error", "Error al iniciar el trabajo.");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Datos inválidos en la solicitud.");
        }
    }

    private void finalizarTrabajo(HttpServletRequest request, HttpSession session, TrabajoDAO trabajoDAO) {
        try {
            String fechaFinString = request.getParameter("fecha_fin");
            String horasString = request.getParameter("horas");

            if (fechaFinString == null || fechaFinString.trim().isEmpty()
                    || horasString == null || horasString.trim().isEmpty()) {
                session.setAttribute("error", "Todos los campos son obligatorios.");
                return;
            }

            int idTrabajo = Integer.parseInt(request.getParameter("id_trabajo"));
            Date fechaFin = Date.valueOf(fechaFinString);
            int horas = Integer.parseInt(horasString);          

            if (horas <= 0) {
                session.setAttribute("error", "Las horas deben ser mayores a 0.");
                return;
            }

            boolean trabajoFinalizado = trabajoDAO.finalizarTrabajo(idTrabajo, fechaFin, horas);
            session.setAttribute("mensaje", trabajoFinalizado);

            if (trabajoFinalizado) {
                session.setAttribute("mensaje", "Trabajo finalizado correctamente.");
            } else {
                session.setAttribute("error", "Error al finalizar el trabajo.");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Error en los datos proporcionados.");
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
        String accion = request.getParameter("accion");
        Connection conn = (Connection) request.getSession().getAttribute("conexion");
        TrabajoDAO trabajoDAO = new TrabajoDAO(conn);

        if (accion == null) {
            response.sendRedirect("dashboard");
            return;
        }

        switch (accion) {
            case "iniciar_trabajo":
                iniciarTrabajo(request, session, trabajoDAO);
                break;
            case "finalizar_trabajo":
                finalizarTrabajo(request, session, trabajoDAO);
                break;
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
