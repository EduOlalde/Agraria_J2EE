package com.ecofield.controlUsuarios;

import com.ecofield.dao.UsuarioDAO;
import com.ecofield.modelos.Usuario;
import com.ecofield.utils.ConexionDB;
import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet que gestiona el login de los usuarios.
 * <p>
 * Este servlet maneja el inicio de sesión de los usuarios, verificando las credenciales proporcionadas y estableciendo
 * una sesión de usuario si las credenciales son correctas.
 * </p>
 */
public class LoginServlet extends HttpServlet {

    /**
     * Inicializa el servlet, estableciendo la conexión a la base de datos en el contexto de la aplicación.
     * 
     * @throws ServletException Si ocurre un error al inicializar la conexión a la base de datos.
     */
    @Override
    public void init() throws ServletException {
        try {
            // Conectamos a la base de datos y la guardamos en el contexto
            Connection conn = ConexionDB.conectar();
            getServletContext().setAttribute("conexion", conn);
        } catch (Exception e) {
            throw new ServletException("Error al inicializar la conexión a la base de datos", e);
        }
    }

    /**
     * Procesa las solicitudes de login, validando las credenciales del usuario.
     * Si el login es exitoso, redirige al usuario al dashboard, de lo contrario, redirige de vuelta al login.
     * 
     * @param request La solicitud HTTP que contiene las credenciales de login.
     * @param response La respuesta HTTP que redirige al usuario según el resultado del login.
     * @throws ServletException Si ocurre un error durante el procesamiento de la solicitud.
     * @throws IOException Si ocurre un error de entrada/salida durante el procesamiento de la solicitud.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Recuperamos la conexión desde el contexto de la aplicación
        Connection conn = (Connection) getServletContext().getAttribute("conexion");

        // Crear el UsuarioDAO con la conexión obtenida
        UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
        Usuario usuario = usuarioDAO.obtenerUsuarioPorNombre(username);

        if (usuario != null && usuario.verificarContrasena(password)) {
            HttpSession session = request.getSession();

            // Guardamos la conexión en la sesión para usarla en futuras peticiones
            session.setAttribute("conexion", conn);
            session.setAttribute("user_id", usuario.getId());
            session.setAttribute("usuario", usuario);

            // Redirigimos al usuario a la página principal (dashboard)
            response.sendRedirect("dashboard");
        } else {
            // Si las credenciales son incorrectas, redirigimos de vuelta al login
            request.getSession().setAttribute("error", "Credenciales incorrectas");
            response.sendRedirect("login.jsp");
        }
    }

    /**
     * Maneja las solicitudes GET.
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
     * Maneja las solicitudes POST.
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
     * Obtiene la descripción del servlet.
     * 
     * @return Una breve descripción del servlet.
     */
    @Override
    public String getServletInfo() {
        return "Servlet para la gestión del login de usuarios.";
    }

    /**
     * Destruye el servlet, cerrando la conexión a la base de datos cuando el servlet es destruido.
     */
    @Override
    public void destroy() {
        // Cerramos la conexión cuando el servlet es destruido
        Connection conn = (Connection) getServletContext().getAttribute("conexion");
        try {
            if (conn != null) {
                conn.close();  // Cerramos la conexión
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
