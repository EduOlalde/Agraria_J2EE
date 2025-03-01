/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ecofield.controlUsuarios;

import com.ecofield.dao.UsuarioDAO;
import com.ecofield.modelos.Usuario;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet encargado de gestionar el panel de usuario, permitiendo la edición
 * de los datos del usuario y la actualización de su contraseña.
 * 
 * Este servlet maneja tanto las solicitudes GET como POST para mostrar y
 * actualizar la información del usuario autenticado.
 * 
 * @author Eduardo Olalde
 */
public class PanelUsuarioServlet extends HttpServlet {

    /**
     * Procesa las solicitudes HTTP para obtener y actualizar los datos del usuario.
     * Si la solicitud es un POST, se intentan actualizar los datos del usuario, 
     * incluida la contraseña si corresponde.
     * 
     * @param request La solicitud HTTP que contiene los parámetros para actualizar
     *                los datos del usuario.
     * @param response La respuesta HTTP, que redirige al usuario al dashboard.
     * @throws ServletException Si ocurre un error durante el procesamiento del servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Verifica si la sesión es válida
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("login.jsp"); // Redirige al login si no hay sesión activa
            return;
        }

        // Obtiene la conexión desde la sesión y el DAO de usuario
        Connection conn = (Connection) session.getAttribute("conexion");
        UsuarioDAO usuarioDAO = new UsuarioDAO(conn);

        // Obtiene el usuario actual de la sesión
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        int userId = usuario.getId();

        // Verifica si la solicitud es un POST para actualizar los datos
        if ("POST".equalsIgnoreCase(request.getMethod())) {

            // Obtiene los parámetros enviados para cambiar la contraseña
            String passActual = request.getParameter("passActual");
            String contrasenia = request.getParameter("contrasenia");
            String repetirContrasenia = request.getParameter("repetir_contrasenia");

            // Verifica la contraseña actual y las contraseñas nuevas
            if (!passActual.equals(usuario.getContrasenia())) {
                session.setAttribute("error", "Contraseña incorrecta.");
            } else if (!contrasenia.isEmpty() && !contrasenia.equals(repetirContrasenia)) {
                session.setAttribute("error", "Las contraseñas no coinciden.");
            } else {

                // Actualiza los datos del usuario
                String nombre = request.getParameter("nombre");
                String telefono = request.getParameter("telefono");
                String email = request.getParameter("email");

                usuario.setNombre(nombre);
                usuario.setTelefono(telefono);
                usuario.setEmail(email);

                if (!contrasenia.isEmpty()) {
                    usuario.setContrasenia(contrasenia);  // Cambia la contraseña si es proporcionada
                }

                // Intenta actualizar los datos del usuario en la base de datos
                String mensaje = usuarioDAO.actualizarUsuario(usuario);
                if (mensaje.equals("Usuario actualizado correctamente.")) {
                    request.getSession().setAttribute("mensaje", mensaje);  // Mensaje de éxito
                } else {
                    request.getSession().setAttribute("error", mensaje);  // Mensaje de error
                }
            }

        }

        // Obtiene los datos del usuario actualizado y los guarda en la sesión
        usuario = usuarioDAO.obtenerUsuarioPorId(userId);
        if (usuario != null) {
            session.setAttribute("usuario", usuario);
        }

        // Redirige al usuario al dashboard después de la actualización
        request.getRequestDispatcher("dashboard").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    
    /**
     * Maneja las solicitudes HTTP <code>GET</code> para mostrar el panel de usuario.
     * Delegamos la solicitud al método procesador principal.
     * 
     * @param request La solicitud HTTP que contiene los detalles del usuario.
     * @param response La respuesta HTTP que redirige al usuario al panel.
     * @throws ServletException Si ocurre un error durante el procesamiento.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Maneja las solicitudes HTTP <code>POST</code> para actualizar los datos del usuario.
     * Delegamos la solicitud al método procesador principal.
     * 
     * @param request La solicitud HTTP que contiene los detalles del usuario a actualizar.
     * @param response La respuesta HTTP que redirige al usuario después de la actualización.
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
        return "Servlet para gestionar la edición de datos del usuario.";
    }// </editor-fold>

}
