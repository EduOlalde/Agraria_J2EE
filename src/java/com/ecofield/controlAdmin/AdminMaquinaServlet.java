/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ecofield.controlAdmin;

import com.ecofield.dao.MaquinaDAO;
import com.ecofield.modelos.Maquina;
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
public class AdminMaquinaServlet extends HttpServlet {

    private void crearMaquina(HttpServletRequest request, HttpSession session, MaquinaDAO maquinaDAO) {
        try {
            int tipoMaquina = Integer.parseInt(request.getParameter("tipo_trabajo_ad_maq"));
            String modelo = request.getParameter("modelo");
            String estado = request.getParameter("estado_ad_maq");

            Maquina maquina = new Maquina(estado, tipoMaquina, modelo);
            boolean creada = maquinaDAO.agregarMaquina(maquina);

            if (creada) {
                session.setAttribute("mensaje", "Máquina creada correctamente");
            } else {
                session.setAttribute("error", "Error al crear la máquina");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Datos inválidos en la solicitud");
        }
    }

    private void eliminarMaquina(HttpServletRequest request, HttpSession session, MaquinaDAO maquinaDAO) {
        try {
            int idMaquina = Integer.parseInt(request.getParameter("id_maquina"));
            boolean eliminada = maquinaDAO.eliminarMaquina(idMaquina);

            if (eliminada) {
                session.setAttribute("mensaje", "Máquina eliminada correctamente");
            } else {
                session.setAttribute("error", "Error al eliminar la máquina: Tiene trabajos asignados");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Error al procesar la solicitud de eliminación");
        }
    }

    private void actualizarMaquina(HttpServletRequest request, HttpSession session, MaquinaDAO maquinaDAO) {
        try {
            int idMaquina = Integer.parseInt(request.getParameter("id_maquina"));
            String estado = request.getParameter("estado_maquina");

            boolean actualizada = maquinaDAO.actualizarEstadoMaquina(idMaquina, estado);

            if (actualizada) {
                session.setAttribute("mensaje", "Máquina actualizada correctamente");
            } else {
                session.setAttribute("error", "Error al actualizar la máquina");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Datos inválidos en la solicitud de actualización");
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
        MaquinaDAO maquinaDAO = new MaquinaDAO(conn);

        if (accion == null) {
            response.sendRedirect("dashboard");
            return;
        }
        switch (accion) {
            case "agregar_maquina":
                crearMaquina(request, session, maquinaDAO);
                break;
            case "eliminar_maquina":
                eliminarMaquina(request, session, maquinaDAO);
                break;
            case "actualizar_estado_maquina":
                actualizarMaquina(request, session, maquinaDAO);
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
