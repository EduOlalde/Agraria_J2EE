/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ecofield.controlMaquinista;

import com.ecofield.dao.TrabajoDAO;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet encargado de gestionar las operaciones de los trabajos de los maquinistas,
 * como iniciar y finalizar trabajos.
 * Permite registrar la fecha de inicio y finalización de un trabajo, así como las horas trabajadas.
 *
 * @author Eduardo Olalde
 */
public class TrabajosMaquinistaServlet extends HttpServlet {

    /**
     * Método encargado de iniciar un trabajo para un maquinista.
     * Valida los datos de entrada, crea la solicitud de inicio del trabajo y la registra en la base de datos.
     * Si hay un error con los datos, se establece un mensaje de error en la sesión.
     * 
     * @param request La solicitud HTTP del cliente.
     * @param session La sesión HTTP del usuario.
     * @param trabajoDAO El objeto DAO utilizado para interactuar con la base de datos de trabajos.
     */
    private void iniciarTrabajo(HttpServletRequest request, HttpSession session, TrabajoDAO trabajoDAO) {
        try {
            String fechaInicioString = request.getParameter("fecha_inicio");

            if (fechaInicioString == null || fechaInicioString.trim().isEmpty()) {
                session.setAttribute("error", "Debe ingresar una fecha de inicio.");
                return;
            }

            int idTrabajo = Integer.parseInt(request.getParameter("id_trabajo"));
            Date fechaInicio = Date.valueOf(fechaInicioString);

            // Intentar iniciar el trabajo en la base de datos
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

    /**
     * Método encargado de finalizar un trabajo para un maquinista.
     * Valida los datos de entrada, crea la solicitud de finalización del trabajo y la registra en la base de datos.
     * Si hay un error con los datos, se establece un mensaje de error en la sesión.
     * 
     * @param request La solicitud HTTP del cliente.
     * @param session La sesión HTTP del usuario.
     * @param trabajoDAO El objeto DAO utilizado para interactuar con la base de datos de trabajos.
     */
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

            // Intentar finalizar el trabajo en la base de datos
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
     * Procesa las solicitudes HTTP tanto de tipo <code>GET</code> como de tipo <code>POST</code>.
     * Este método maneja la lógica de inicio y finalización de los trabajos según la acción proporcionada en la solicitud.
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
        String accion = request.getParameter("accion");
        Connection conn = (Connection) request.getSession().getAttribute("conexion");
        TrabajoDAO trabajoDAO = new TrabajoDAO(conn);

        if (accion == null) {
            response.sendRedirect("dashboard");
            return;
        }

        // Realizar la acción correspondiente según el parámetro de acción
        switch (accion) {
            case "iniciar_trabajo":
                iniciarTrabajo(request, session, trabajoDAO);
                break;
            case "finalizar_trabajo":
                finalizarTrabajo(request, session, trabajoDAO);
                break;
        }

        // Redirigir al dashboard después de procesar la acción
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
        return "Servlet para gestionar los trabajos de los maquinistas, incluyendo el inicio y la finalización de trabajos.";
    }
}
