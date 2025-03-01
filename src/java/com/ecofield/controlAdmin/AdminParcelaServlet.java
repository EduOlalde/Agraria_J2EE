/*
 * Servlet para la administración de parcelas en la aplicación EcoField.
 * Permite agregar y eliminar parcelas mediante peticiones HTTP.
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
 * Servlet encargado de gestionar la creación y eliminación de parcelas.
 * Se accede a él mediante las acciones "agregar_parcela" y "eliminar_parcela".
 */
public class AdminParcelaServlet extends HttpServlet {

    /**
     * Crea una nueva parcela a partir de los parámetros recibidos en la solicitud.
     * 
     * @param request Objeto HttpServletRequest con los datos de la solicitud.
     * @param session Sesión actual para almacenar mensajes de respuesta.
     * @param parcelaDAO Objeto DAO para la gestión de parcelas en la base de datos.
     */
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

    /**
     * Elimina una parcela específica de la base de datos.
     * 
     * @param request Objeto HttpServletRequest con los datos de la solicitud.
     * @param session Sesión actual para almacenar mensajes de respuesta.
     * @param parcelaDAO Objeto DAO para la gestión de parcelas en la base de datos.
     */
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
     * Procesa las solicitudes HTTP GET y POST.
     * 
     * @param request Objeto HttpServletRequest con los datos de la solicitud.
     * @param response Objeto HttpServletResponse para enviar respuestas al cliente.
     * @throws ServletException si ocurre un error en la gestión del servlet.
     * @throws IOException si ocurre un error de entrada/salida.
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

    /**
     * Maneja las solicitudes HTTP GET.
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Maneja las solicitudes HTTP POST.
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Retorna una breve descripción del servlet.
     * @return 
     */
    @Override
    public String getServletInfo() {
        return "Servlet para la gestión de parcelas en EcoField";
    }
}
