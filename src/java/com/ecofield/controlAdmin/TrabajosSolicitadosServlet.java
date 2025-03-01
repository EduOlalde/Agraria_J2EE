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
 * Servlet para gestionar las solicitudes de trabajos.
 * Permite asignar y rechazar trabajos en función de las solicitudes recibidas.
 * 
 * @author Eduardo Olalde
 */
public class TrabajosSolicitadosServlet extends HttpServlet {

    /**
     * Asigna un trabajo a un maquinista y una máquina específica.
     * 
     * @param request Objeto HttpServletRequest con los datos de la solicitud.
     * @param session Sesión del usuario para almacenar mensajes de estado.
     * @param trabajoDAO DAO para gestionar la asignación de trabajos en la base de datos.
     */
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

    /**
     * Rechaza una solicitud de trabajo específica.
     * 
     * @param request Objeto HttpServletRequest con los datos de la solicitud.
     * @param session Sesión del usuario para almacenar mensajes de estado.
     * @param trabajoDAO DAO para gestionar el rechazo de trabajos en la base de datos.
     */
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
     * Procesa solicitudes HTTP tanto de tipo GET como POST.
     * 
     * @param request Objeto HttpServletRequest con la solicitud del cliente.
     * @param response Objeto HttpServletResponse para enviar la respuesta al cliente.
     * @throws ServletException Si ocurre un error específico del servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
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

    /**
     * Maneja las solicitudes HTTP GET.
     * 
     * @param request Objeto HttpServletRequest con la solicitud del cliente.
     * @param response Objeto HttpServletResponse para enviar la respuesta al cliente.
     * @throws ServletException Si ocurre un error específico del servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Maneja las solicitudes HTTP POST.
     * 
     * @param request Objeto HttpServletRequest con la solicitud del cliente.
     * @param response Objeto HttpServletResponse para enviar la respuesta al cliente.
     * @throws ServletException Si ocurre un error específico del servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Devuelve una descripción breve del servlet.
     * 
     * @return Una cadena con la descripción del servlet.
     */
    @Override
    public String getServletInfo() {
        return "Servlet para gestionar la asignación y rechazo de trabajos solicitados.";
    }
}
