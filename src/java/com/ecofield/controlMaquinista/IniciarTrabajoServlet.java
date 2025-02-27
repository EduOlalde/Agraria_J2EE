/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ecofield.controlMaquinista;

import com.ecofield.dao.TrabajoDAO;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Eduardo Olalde
 */
public class IniciarTrabajoServlet extends HttpServlet {

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
        Integer idMaquinista = (Integer) session.getAttribute("user_id");
        Connection conn = (Connection) session.getAttribute("conexion");

        if (conn == null) {
            session.setAttribute("error", "Error: No hay conexión a la base de datos.");
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
            return;
        }

        if (idMaquinista == null) {
            session.setAttribute("error", "Error: No hay usuario autenticado.");
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
            return;
        }

        int idTrabajo;
        String fechaInicioString = request.getParameter("fecha_inicio");

        if (fechaInicioString == null || fechaInicioString.trim().isEmpty()) {
            session.setAttribute("error", "Debe ingresar una fecha de inicio.");
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
            return;
        }

        try {
            idTrabajo = Integer.parseInt(request.getParameter("id_trabajo"));
            Date fechaInicio = Date.valueOf(fechaInicioString);

            TrabajoDAO trabajoDAO = new TrabajoDAO(conn);
            boolean trabajoIniciado = trabajoDAO.iniciarTrabajo(idTrabajo, fechaInicio, idMaquinista);

            if (trabajoIniciado) {
                session.setAttribute("mensaje", "Trabajo iniciado correctamente.");
                response.sendRedirect("dashboard.jsp");
            } else {
                session.setAttribute("error", "Hubo un error al iniciar el trabajo.");
                request.getRequestDispatcher("dashboard.jsp").forward(request, response);
            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            session.setAttribute("error", "Error al procesar la solicitud.");
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
        }
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
