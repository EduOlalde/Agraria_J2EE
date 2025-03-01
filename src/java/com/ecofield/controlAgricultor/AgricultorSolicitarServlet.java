/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ecofield.controlAgricultor;

import com.ecofield.dao.TrabajoSolicitadoDAO;
import com.ecofield.modelos.TrabajoSolicitado;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet encargado de gestionar las solicitudes de trabajo de los agricultores.
 * Permite registrar una solicitud de trabajo en la base de datos.
 *
 * @author Eduardo Olalde
 */
public class AgricultorSolicitarServlet extends HttpServlet {

    /**
     * Método que maneja la creación de una solicitud de trabajo de un agricultor.
     * Recibe los parámetros del formulario, crea un objeto de solicitud de trabajo
     * y lo inserta en la base de datos mediante el DAO correspondiente.
     * 
     * @param request La solicitud HTTP del cliente.
     * @param session La sesión HTTP del usuario.
     * @param trabajoDAO El objeto DAO utilizado para interactuar con la base de datos de trabajos solicitados.
     */
    private void solicitarTrabajo(HttpServletRequest request, HttpSession session, TrabajoSolicitadoDAO trabajoDAO) {

        // Obtener los parámetros del formulario
        int numParcela = Integer.parseInt(request.getParameter("parcela_id"));
        int idTipoTrabajo = Integer.parseInt(request.getParameter("tipo_trabajo_agri_solicita"));
        int propietario = Integer.parseInt(request.getParameter("user_id"));

        // Crear una nueva solicitud de trabajo
        TrabajoSolicitado solicitud = new TrabajoSolicitado(numParcela, propietario, idTipoTrabajo);

        // Insertar la solicitud de trabajo en la base de datos
        boolean exito = trabajoDAO.crearSolicitud(solicitud);

        // Establecer mensaje en la sesión dependiendo del resultado de la inserción
        if (exito) {
            session.setAttribute("mensaje", "Solicitud de trabajo realizada correctamente.");
        } else {
            session.setAttribute("error", "Error al realizar la solicitud de trabajo.");
        }
    }

    /**
     * Procesa las solicitudes HTTP <code>GET</code> y <code>POST</code>.
     * Este método maneja el flujo principal de la solicitud, que incluye la creación
     * de la solicitud de trabajo y la redirección al dashboard.
     *
     * @param request La solicitud HTTP del cliente.
     * @param response La respuesta HTTP que se enviará al cliente.
     * @throws ServletException Si ocurre un error específico del servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);
        Connection conn = (Connection) session.getAttribute("conexion");
        TrabajoSolicitadoDAO trabajoDAO = new TrabajoSolicitadoDAO(conn);

        // Llamar al método para realizar la solicitud de trabajo
        solicitarTrabajo(request, session, trabajoDAO);

        // Redirigir al dashboard después de procesar la solicitud
        response.sendRedirect("dashboard");
    }

    /**
     * Maneja las solicitudes HTTP <code>GET</code>.
     * 
     * @param request La solicitud HTTP del cliente.
     * @param response La respuesta HTTP que se enviará al cliente.
     * @throws ServletException Si ocurre un error específico del servlet.
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
     * @throws ServletException Si ocurre un error específico del servlet.
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
        return "Servlet para gestionar las solicitudes de trabajo de los agricultores.";
    }
}
