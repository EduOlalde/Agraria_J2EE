/*
 * Servlet para la gestión de usuarios en la plataforma EcoField.
 * Permite crear, modificar y eliminar usuarios, además de asignarles roles.
 */
package com.ecofield.controlAdmin;

import com.ecofield.dao.UsuarioDAO;
import com.ecofield.modelos.Rol;
import com.ecofield.modelos.Usuario;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet encargado de la administración de usuarios en el sistema.
 * Maneja las operaciones de creación, modificación y eliminación de usuarios.
 * 
 * @author Eduardo Olalde
 */
public class AdminUsuarioServlet extends HttpServlet {

    /**
     * Crea un nuevo usuario con los datos proporcionados en la petición.
     * 
     * @param request Objeto HttpServletRequest con los parámetros del usuario.
     * @param session Sesión actual del usuario.
     * @param usuarioDAO Instancia de UsuarioDAO para realizar operaciones en la base de datos.
     */
    private void crearUsuario(HttpServletRequest request, HttpSession session, UsuarioDAO usuarioDAO) {
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        String contrasenia = request.getParameter("contrasenia");
        boolean habilitado = request.getParameter("habilitado") != null;
        String[] rolesSeleccionados = request.getParameterValues("roles");

        Usuario usuario = new Usuario(0, nombre, email, contrasenia, telefono, habilitado);
        List<Rol> roles = obtenerRolesDesdeParametros(rolesSeleccionados);

        String mensaje = usuarioDAO.registrarUsuario(usuario, roles);

        if (mensaje.equals("Usuario registrado correctamente.")) {
            session.setAttribute("mensaje", mensaje);
        } else {
            session.setAttribute("error", mensaje);
        }
    }

    /**
     * Modifica un usuario existente con los datos proporcionados en la petición.
     * 
     * @param request Objeto HttpServletRequest con los parámetros del usuario.
     * @param session Sesión actual del usuario.
     * @param usuarioDAO Instancia de UsuarioDAO para realizar operaciones en la base de datos.
     */
    private void modificarUsuario(HttpServletRequest request, HttpSession session, UsuarioDAO usuarioDAO) {
        int idUsuario = Integer.parseInt(request.getParameter("id_usuario"));
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        boolean habilitado = request.getParameter("habilitado") != null;

        String[] rolesSeleccionados = request.getParameterValues("roles");
        List<Rol> roles = obtenerRolesDesdeParametros(rolesSeleccionados);

        Usuario usuario = new Usuario(idUsuario, nombre, email, telefono, habilitado, roles);

        String mensaje = usuarioDAO.actualizarUsuario(usuario, roles);

        if (mensaje.equals("Usuario actualizado correctamente.")) {
            usuario = usuarioDAO.obtenerUsuarioPorId(idUsuario);
            Usuario admin = (Usuario) session.getAttribute("usuario");
            if(usuario.getId() == admin.getId()) session.setAttribute("usuario", usuario);
            session.setAttribute("mensaje", mensaje);
        } else {
            session.setAttribute("error", mensaje);
        }
    }

    /**
     * Elimina un usuario del sistema según el ID proporcionado.
     * 
     * @param request Objeto HttpServletRequest con el ID del usuario a eliminar.
     * @param session Sesión actual del usuario.
     * @param usuarioDAO Instancia de UsuarioDAO para realizar operaciones en la base de datos.
     */
    private void eliminarUsuario(HttpServletRequest request, HttpSession session, UsuarioDAO usuarioDAO) {
        int idUsuario = Integer.parseInt(request.getParameter("id_usuario"));
        String mensaje = usuarioDAO.eliminarUsuario(idUsuario);

        if (mensaje.equals("Usuario eliminado correctamente.")) {
            session.setAttribute("mensaje", mensaje);
        } else {
            session.setAttribute("error", mensaje);
        }
    }

    /**
     * Convierte los parámetros recibidos en una lista de objetos Rol.
     * 
     * @param rolesSeleccionados Array de strings con los IDs de los roles seleccionados.
     * @return Lista de objetos Rol.
     */
    private List<Rol> obtenerRolesDesdeParametros(String[] rolesSeleccionados) {
        List<Rol> roles = new ArrayList<>();
        if (rolesSeleccionados != null) {
            for (String idRol : rolesSeleccionados) {
                roles.add(new Rol(Integer.parseInt(idRol), ""));
            }
        }
        return roles;
    }

    /**
     * Procesa solicitudes HTTP GET y POST.
     * 
     * @param request Objeto HttpServletRequest con la solicitud del cliente.
     * @param response Objeto HttpServletResponse con la respuesta al cliente.
     * @throws ServletException Si ocurre un error específico del servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);
        String accion = request.getParameter("accion");
        Connection conn = (Connection) request.getSession().getAttribute("conexion");
        UsuarioDAO usuarioDAO = new UsuarioDAO(conn);

        if (accion == null) {
            response.sendRedirect("dashboard");
            return;
        }

        switch (accion) {
            case "crear":
                crearUsuario(request, session, usuarioDAO);
                break;
            case "modificar":
                modificarUsuario(request, session, usuarioDAO);
                break;
            case "eliminar":
                eliminarUsuario(request, session, usuarioDAO);
                break;
        }

        response.sendRedirect("dashboard");
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
        return "Servlet para la gestión de usuarios en EcoField.";
    }
}
