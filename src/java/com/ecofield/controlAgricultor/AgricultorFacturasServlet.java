/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ecofield.controlAgricultor;

import com.ecofield.dao.FacturaDAO;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet para gestionar las facturas de los agricultores.
 * Permite realizar el pago de una factura y actualizar su estado en la base de datos.
 *
 * @author Eduardo Olalde
 */
public class AgricultorFacturasServlet extends HttpServlet {

    /**
     * Método que actualiza el estado de una factura a "Pagada" en la base de datos.
     * Si se produce un error al intentar pagar, se muestra un mensaje de error.
     * 
     * @param request La solicitud HTTP del cliente.
     * @param session La sesión HTTP del usuario.
     * @param facturaDAO El objeto DAO para la gestión de facturas en la base de datos.
     */
    private void pagarFactura(HttpServletRequest request, HttpSession session, FacturaDAO facturaDAO) {
        try {
            int idFactura = Integer.parseInt(request.getParameter("id_factura"));
            boolean pagada = facturaDAO.actualizarEstadoFactura(idFactura, "Pagada");

            if (pagada) {
                session.setAttribute("mensaje", "Factura pagada correctamente.");
            } else {
                session.setAttribute("error", "Error al pagar la factura.");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("error", "ID de factura inválido.");
        }
    }

    /**
     * Procesa las solicitudes HTTP <code>GET</code> y <code>POST</code>.
     * Este método se encarga de gestionar el pago de la factura y redirigir al usuario al dashboard.
     *
     * @param request La solicitud HTTP del cliente.
     * @param response La respuesta HTTP que se enviará al cliente.
     * @throws ServletException Si ocurre un error específico de servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);
        Connection conn = (Connection) request.getSession().getAttribute("conexion");
        FacturaDAO facturaDAO = new FacturaDAO(conn);

        pagarFactura(request, session, facturaDAO);

        response.sendRedirect("dashboard");
    }

    /**
     * Maneja las solicitudes HTTP <code>GET</code>.
     * 
     * @param request La solicitud HTTP del cliente.
     * @param response La respuesta HTTP que se enviará al cliente.
     * @throws ServletException Si ocurre un error específico de servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Maneja las solicitudes HTTP <code>POST</code>.
     * 
     * @param request La solicitud HTTP del cliente.
     * @param response La respuesta HTTP que se enviará al cliente.
     * @throws ServletException Si ocurre un error específico de servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Devuelve una descripción corta del servlet.
     *
     * @return Una cadena que contiene la descripción del servlet.
     */
    @Override
    public String getServletInfo() {
        return "Servlet para gestionar el pago de facturas de los agricultores.";
    }
}
