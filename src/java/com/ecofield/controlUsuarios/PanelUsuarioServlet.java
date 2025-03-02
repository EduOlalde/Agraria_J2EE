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
 * Servlet encargado de gestionar el panel de usuario, permitiendo la edición de
 * los datos del usuario y la actualización de su contraseña.
 *
 * @author Eduardo Olalde
 */
public class PanelUsuarioServlet extends HttpServlet {

    /**
     * Actualiza los datos del usuario (incluida la contraseña si es necesario).
     *
     * @param request La solicitud HTTP que contiene los parámetros de
     * actualización.
     * @param session La sesión del usuario para almacenar mensajes de estado.
     * @param usuarioDAO DAO para actualizar los datos del usuario en la base de
     * datos.
     */
    private void actualizarUsuario(HttpServletRequest request, HttpSession session, UsuarioDAO usuarioDAO) {
        try {
            // Obtiene los parámetros enviados para cambiar la contraseña
            String passActual = request.getParameter("passActual");
            String contrasenia = request.getParameter("contrasenia");
            String repetirContrasenia = request.getParameter("repetir_contrasenia");

            // Verifica la contraseña actual y las contraseñas nuevas
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            if (!passActual.equals(usuario.getContrasenia())) {
                session.setAttribute("error", "Contraseña incorrecta.");
                return;
            } else if (!contrasenia.isEmpty() && !contrasenia.equals(repetirContrasenia)) {
                session.setAttribute("error", "Las contraseñas no coinciden.");
                return;
            }

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
                session.setAttribute("mensaje", mensaje);  // Mensaje de éxito
            } else {
                session.setAttribute("error", mensaje);  // Mensaje de error
            }

        } catch (Exception e) {
            session.setAttribute("error", "Error al procesar la actualización.");
        }
    }

    /**
     * Procesa solicitudes HTTP tanto de tipo GET como POST.
     *
     * @param request Objeto HttpServletRequest con la solicitud del cliente.
     * @param response Objeto HttpServletResponse para enviar la respuesta al
     * cliente.
     * @throws ServletException Si ocurre un error específico del servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);
        Connection conn = (Connection) request.getSession().getAttribute("conexion");
        UsuarioDAO usuarioDAO = new UsuarioDAO(conn);

        // Verifica si el usuario está autenticado
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Si la solicitud es POST, actualiza los datos del usuario
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            actualizarUsuario(request, session, usuarioDAO);
        }

        // Actualiza la sesión con los datos del usuario
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        usuario = usuarioDAO.obtenerUsuarioPorId(usuario.getId());
        session.setAttribute("usuario", usuario);

        // Redirige al panel de usuario después de la actualización
        response.sendRedirect("dashboard");
    }

    /**
     * Maneja las solicitudes HTTP GET.
     *
     * @param request Objeto HttpServletRequest con la solicitud del cliente.
     * @param response Objeto HttpServletResponse para enviar la respuesta al
     * cliente.
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
     * @param response Objeto HttpServletResponse para enviar la respuesta al
     * cliente.
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
        return "Servlet para gestionar la edición de datos del usuario.";
    }
}
