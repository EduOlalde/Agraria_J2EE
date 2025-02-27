package com.ecofield.controlUsuarios;

import com.ecofield.dao.UsuarioDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;

/**
 *
 * @author Eduardo Olalde
 */
public class RegistroServlet extends HttpServlet {

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

        // Obtén los parámetros del formulario
        String nombre = request.getParameter("nombre");
        String contrasenia = request.getParameter("contrasenia");
        String telefono = request.getParameter("telefono");
        String email = request.getParameter("email");

        // Recuperar la conexión desde el ServletContext
        Connection conn = (Connection) getServletContext().getAttribute("conexion");
        
        // Verifica si la conexión está disponible
        if (conn == null) {
            request.getSession().setAttribute("error", "No se pudo obtener la conexión a la base de datos.");
            response.sendRedirect("registro.jsp");
            return;
        }

        // Crear una instancia de UsuarioDAO y pasarle la conexión
        UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
        
        // Registrar al usuario
        boolean exito = usuarioDAO.registrarUsuario(nombre, contrasenia, telefono, email);
        if (exito) {
            request.getSession().setAttribute("mensaje", "Usuario registrado correctamente");
            response.sendRedirect("login.jsp");
        } else {
            request.getSession().setAttribute("error", "Error en el registro");
            response.sendRedirect("registro.jsp");
        }
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
