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
 */
public class LoginServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        // Inicialización de la conexión en el contexto
        try {
            // Conectamos a la base de datos y la guardamos en el contexto
            Connection conn = ConexionDB.conectar();
            getServletContext().setAttribute("conexion", conn);
        } catch (Exception e) {
            throw new ServletException("Error al inicializar la conexión a la base de datos", e);
        }
    }

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

            // Redirigimos al usuario a la página principal
            response.sendRedirect("DashboardServlet");
        } else {
            // Si las credenciales son incorrectas, redirigimos de vuelta al login
            request.getSession().setAttribute("error", "Credenciales incorrectas");
            response.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet para la gestión del login de usuarios.";
    }

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
