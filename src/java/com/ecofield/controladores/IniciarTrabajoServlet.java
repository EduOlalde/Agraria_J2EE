/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ecofield.controladores;

import com.ecofield.dao.TrabajoDAO;
import com.ecofield.modelos.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
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
        // Obtener datos de la sesión
        HttpSession session = request.getSession();
        int idMaquinista = (int) session.getAttribute("user_id");

        // Obtener los parámetros del formulario
        int idTrabajo = Integer.parseInt(request.getParameter("id_trabajo"));
        String fechaInicioString = request.getParameter("fecha_inicio");
        Date fechaInicio = Date.valueOf(fechaInicioString);

        // Llamar al DAO para iniciar el trabajo
        TrabajoDAO trabajoDAO = new TrabajoDAO();
        boolean trabajoIniciado = false;
        try {
            trabajoIniciado = trabajoDAO.iniciarTrabajo(idTrabajo, fechaInicio, idMaquinista);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Redirigir al JSP correspondiente con un mensaje de éxito o error
        if (trabajoIniciado) {
            session.setAttribute("mensaje", "Trabajo iniciado correctamente.");
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);  // Redirige a la misma página si el trabajo fue iniciado
        } else {
            session.setAttribute("error", "Hubo un error al iniciar el trabajo.");
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);  // Redirige con un mensaje de error
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
