/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ecofield.controlUsuarios;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet encargado de manejar la desconexión de un usuario.
 * <p>
 * Este servlet invalida la sesión del usuario y lo redirige a la página de login.
 * </p>
 * 
 * @author Eduardo Olalde
 */
public class LogoutServlet extends HttpServlet {

    /**
     * Procesa las solicitudes de logout, invalidando la sesión del usuario y redirigiéndolo al login.
     * 
     * @param request La solicitud HTTP que contiene los detalles de la sesión.
     * @param response La respuesta HTTP que redirige al usuario después de cerrar sesión.
     * @throws ServletException Si ocurre un error durante el procesamiento del servlet.
     * @throws IOException Si ocurre un error de entrada/salida durante el procesamiento.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);  // Recupera la sesión sin crear una nueva
        
        if (session != null) {
            session.invalidate();  // Invalida la sesión actual
        }
        
        response.sendRedirect("login.jsp");  // Redirige al usuario al login después de cerrar sesión
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    
    /**
     * Maneja las solicitudes HTTP <code>GET</code> para el logout.
     * Delegamos la solicitud al método procesador principal.
     * 
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @throws ServletException Si ocurre un error durante el procesamiento.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Maneja las solicitudes HTTP <code>POST</code> para el logout.
     * Delegamos la solicitud al método procesador principal.
     * 
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @throws ServletException Si ocurre un error durante el procesamiento.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Retorna una breve descripción del servlet.
     * 
     * @return Una cadena que contiene una descripción corta del servlet.
     */
    @Override
    public String getServletInfo() {
        return "Servlet para manejar la desconexión de un usuario.";
    }// </editor-fold>

}
