/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ecofield.controlAdmin;

import com.ecofield.dao.MaquinistaDAO;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet encargado de la administración de los maquinistas.
 * Permite actualizar las especialidades de un maquinista en la base de datos.
 * 
 * @author Eduardo Olalde
 */
public class AdminMaquinistasServlet extends HttpServlet {

    /**
     * Procesa las solicitudes HTTP GET y POST.
     * 
     * @param request Objeto que contiene la solicitud del cliente.
     * @param response Objeto que contiene la respuesta para el cliente.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Obtiene la conexión de la sesión
        Connection conn = (Connection) request.getSession().getAttribute("conexion");
        MaquinistaDAO maquinistaDAO = new MaquinistaDAO(conn);

        // Obtiene el ID del maquinista y las especialidades seleccionadas
        int idMaquinista = Integer.parseInt(request.getParameter("id_maquinista"));
        String[] especialidades = request.getParameterValues("especialidades");

        // Convierte las especialidades en una lista de enteros
        List<Integer> nuevasEspecialidades = new ArrayList<>();
        if (especialidades != null) {
            for (String especialidad : especialidades) {
                nuevasEspecialidades.add(Integer.valueOf(especialidad));
            }
        }
      
        // Actualiza las especialidades del maquinista en la base de datos
        maquinistaDAO.actualizarEspecialidades(idMaquinista, nuevasEspecialidades);
        request.getSession().setAttribute("mensaje", "Especialidades actualizadas");
        response.sendRedirect("dashboard");
    }

    /**
     * Maneja las solicitudes HTTP GET.
     * 
     * @param request Objeto que contiene la solicitud del cliente.
     * @param response Objeto que contiene la respuesta para el cliente.
     * @throws ServletException Si ocurre un error en el servlet.
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
     * @param request Objeto que contiene la solicitud del cliente.
     * @param response Objeto que contiene la respuesta para el cliente.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Devuelve una breve descripción del servlet.
     * 
     * @return Una cadena de texto con la descripción del servlet.
     */
    @Override
    public String getServletInfo() {
        return "Servlet para la administración de maquinistas y sus especialidades.";
    }
}
