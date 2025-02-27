/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ecofield.controlAdmin;

import com.ecofield.dao.ParcelaDAO;
import com.ecofield.modelos.Parcela;
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
public class AdminParcelaServlet extends HttpServlet {

    private void crearParcela(HttpServletRequest request, HttpSession session, ParcelaDAO parcelaDAO) {
        try {
            String catastro = request.getParameter("ID_Catastro");
            double extension = Double.parseDouble(request.getParameter("Extension"));
            int propietario = Integer.parseInt(request.getParameter("Propietario"));

            Parcela parcela = new Parcela(catastro, extension, propietario);
            boolean creada = parcelaDAO.crearParcela(parcela);

            if (creada) {
                session.setAttribute("mensaje", "Parcela creada correctamente");
            } else {
                session.setAttribute("error", "Error al crear la parcela");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Datos inválidos en la solicitud");
        }
    }

    private void eliminarParcela(HttpServletRequest request, HttpSession session, ParcelaDAO parcelaDAO) {
        try {
            String idCatastro = request.getParameter("id_catastro");
            boolean eliminada = parcelaDAO.eliminarParcela(idCatastro);

            if (eliminada) {
                session.setAttribute("mensaje", "Parcela eliminada correctamente");
            } else {
                session.setAttribute("error", "Error al eliminar la parcela: Tiene trabajos asignados");
            }
        } catch (Exception e) {
            session.setAttribute("error", "Error al procesar la solicitud de eliminación");
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
        ParcelaDAO parcelaDAO = new ParcelaDAO(conn);

        if (accion == null) {
            response.sendRedirect("dashboard");
            return;
        }
        switch (accion) {
            case "agregar_parcela":
                crearParcela(request, session, parcelaDAO);
                break;
            case "eliminar_parcela":
                eliminarParcela(request, session, parcelaDAO);
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
