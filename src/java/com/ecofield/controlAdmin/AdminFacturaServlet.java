/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ecofield.controlAdmin;

import com.ecofield.dao.FacturaDAO;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet para la administración de facturas en el sistema EcoField.
 * Permite la generación y actualización del estado de las facturas.
 * 
 * @author Eduardo Olalde
 */
public class AdminFacturaServlet extends HttpServlet {

    /**
     * Genera una factura actualizando su estado en la base de datos.
     * 
     * @param request  Objeto HttpServletRequest con los datos de la solicitud.
     * @param session  Sesión del usuario actual.
     * @param facturaDAO DAO para acceder a los datos de las facturas.
     */
    private void generarFactura(HttpServletRequest request, HttpSession session, FacturaDAO facturaDAO) {
        try {
            int idFactura = Integer.parseInt(request.getParameter("id_factura"));
            boolean generada = facturaDAO.actualizarEstadoFactura(idFactura, "Pendiente de pagar");

            if (generada) {
                session.setAttribute("mensaje", "Factura generada correctamente.");
            } else {
                session.setAttribute("error", "Error al generar la factura.");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("error", "ID de trabajo inválido.");
        }
    }

    /**
     * Procesa las solicitudes HTTP <code>GET</code> y <code>POST</code>.
     *
     * @param request  Objeto HttpServletRequest con la solicitud.
     * @param response Objeto HttpServletResponse para enviar la respuesta.
     * @throws ServletException si ocurre un error en el servlet.
     * @throws IOException si ocurre un error de entrada/salida.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);
        Connection conn = (Connection) request.getSession().getAttribute("conexion");
        FacturaDAO facturaDAO = new FacturaDAO(conn);

        generarFactura(request, session, facturaDAO);

        response.sendRedirect("dashboard");
    }

    /**
     * Maneja las solicitudes HTTP <code>GET</code>.
     *
     * @param request  Objeto HttpServletRequest con la solicitud.
     * @param response Objeto HttpServletResponse para enviar la respuesta.
     * @throws ServletException si ocurre un error en el servlet.
     * @throws IOException si ocurre un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Maneja las solicitudes HTTP <code>POST</code>.
     *
     * @param request  Objeto HttpServletRequest con la solicitud.
     * @param response Objeto HttpServletResponse para enviar la respuesta.
     * @throws ServletException si ocurre un error en el servlet.
     * @throws IOException si ocurre un error de entrada/salida.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Devuelve una breve descripción del servlet.
     *
     * @return Una cadena con la descripción del servlet.
     */
    @Override
    public String getServletInfo() {
        return "Servlet para la gestión de facturas en EcoField";
    }
}
