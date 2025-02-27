/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ecofield.controlUsuarios;

import com.ecofield.dao.UsuarioDAO;
import com.ecofield.modelos.Usuario;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Eduardo Olalde
 */
public class PanelUsuarioServlet extends HttpServlet {

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
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Connection conn = (Connection) session.getAttribute("conexion");
        UsuarioDAO usuarioDAO = new UsuarioDAO(conn);

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        int userId = usuario.getId();

        if ("POST".equalsIgnoreCase(request.getMethod())) {
            
            String passActual = request.getParameter("passActual");
            String contrasenia = request.getParameter("contrasenia");
            String repetirContrasenia = request.getParameter("repetir_contrasenia");

            if (!passActual.equals(usuario.getContrasenia())) {
                session.setAttribute("error", "Contraseña incorrecta.");
            } else if (!contrasenia.isEmpty() && !contrasenia.equals(repetirContrasenia)) {
                session.setAttribute("error", "Las contraseñas no coinciden.");
            } else {
                
                String nombre = request.getParameter("nombre");
                String telefono = request.getParameter("telefono");
                String email = request.getParameter("email");
                
                usuario.setNombre(nombre);
                usuario.setTelefono(telefono);
                usuario.setEmail(email);
                
                if(!contrasenia.isEmpty()){
                    usuario.setContrasenia(contrasenia);
                }
                // Intentar actualizar el usuario
                boolean actualizado = usuarioDAO.actualizarUsuario(usuario);
                session.setAttribute("mensaje", actualizado ? "Datos actualizados con éxito." : "");
            }           
          
            

        }

        // Obtener usuario actualizado y enviarlo a la vista
        usuario = usuarioDAO.obtenerUsuarioPorId(userId);
        if (usuario != null) {
            session.setAttribute("usuario", usuario);
        }

        request.getRequestDispatcher("dashboard.jsp").forward(request, response);

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
