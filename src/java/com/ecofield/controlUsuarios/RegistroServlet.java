package com.ecofield.controlUsuarios;

import com.ecofield.dao.UsuarioDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;

/**
 * Servlet encargado de gestionar el registro de nuevos usuarios en el sistema.
 * Este servlet procesa tanto las solicitudes GET como POST para registrar un nuevo usuario.
 * En una solicitud POST, se registran los datos proporcionados por el usuario en el formulario.
 * 
 * @author Eduardo Olalde
 */
public class RegistroServlet extends HttpServlet {

    /**
     * Procesa las solicitudes HTTP tanto de tipo GET como POST para registrar a un nuevo usuario.
     * En el caso de una solicitud POST, los datos del usuario son obtenidos desde el formulario y
     * se pasa a la capa de persistencia (UsuarioDAO) para ser registrados en la base de datos.
     * 
     * @param request La solicitud HTTP que contiene los parámetros del formulario de registro.
     * @param response La respuesta HTTP que redirige al usuario según el resultado del registro.
     * @throws ServletException Si ocurre un error específico del servlet durante el procesamiento.
     * @throws IOException Si ocurre un error de entrada/salida durante la solicitud.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Obtén los parámetros del formulario de registro
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

        // Registrar al usuario en la base de datos
        String mensaje = usuarioDAO.registrarUsuario(nombre, contrasenia, telefono, email);
        if (mensaje.equals("Usuario registrado correctamente.")) {
            // Si el registro fue exitoso, redirige al login con un mensaje de éxito
            request.getSession().setAttribute("mensaje", mensaje);
            response.sendRedirect("login.jsp");
        } else {
            // Si ocurrió un error, redirige al registro con un mensaje de error
            request.getSession().setAttribute("error", mensaje);
            response.sendRedirect("registro.jsp");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    
    /**
     * Maneja las solicitudes HTTP GET para mostrar el formulario de registro.
     * Delegamos la solicitud al método de procesamiento principal.
     * 
     * @param request La solicitud HTTP con los detalles para el formulario de registro.
     * @param response La respuesta HTTP que redirige a la página de registro.
     * @throws ServletException Si ocurre un error durante el procesamiento.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Maneja las solicitudes HTTP POST para registrar un nuevo usuario.
     * Delegamos la solicitud al método de procesamiento principal.
     * 
     * @param request La solicitud HTTP con los detalles del usuario a registrar.
     * @param response La respuesta HTTP que redirige después del registro.
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
        return "Servlet para gestionar el registro de usuarios.";
    }// </editor-fold>

}
