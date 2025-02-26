/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ecofield.controlAdmin;

import com.ecofield.dao.UsuarioDAO;
import com.ecofield.modelos.Rol;
import com.ecofield.modelos.Usuario;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Eduardo Olalde
 */
public class AdminUsuarioServlet extends HttpServlet {

    private boolean crearUsuario(HttpServletRequest request, UsuarioDAO usuarioDAO) {
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        String contrasenia = request.getParameter("contrasenia");
        boolean habilitado = request.getParameter("habilitado") != null;
        String[] rolesSeleccionados = request.getParameterValues("roles");

        Usuario usuario = new Usuario(0, nombre, email, contrasenia, telefono, habilitado);
        List<Rol> roles = obtenerRolesDesdeParametros(rolesSeleccionados);

        return usuarioDAO.registrarUsuario(usuario, roles);

    }

    private boolean modificarUsuario(HttpServletRequest request, UsuarioDAO usuarioDAO) {
        int idUsuario = Integer.parseInt(request.getParameter("id_usuario"));
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        boolean habilitado = request.getParameter("habilitado") != null;
        String[] rolesSeleccionados = request.getParameterValues("roles");

        Usuario usuario = new Usuario(idUsuario, nombre, email, "", telefono, habilitado);
        usuario.setRoles(obtenerRolesDesdeParametros(rolesSeleccionados));

        return usuarioDAO.actualizarUsuario(usuario);
    }

    private void eliminarUsuario(HttpServletRequest request, HttpSession session, UsuarioDAO usuarioDAO) {
        int idUsuario = Integer.parseInt(request.getParameter("id_usuario"));
        String mensaje = usuarioDAO.eliminarUsuario(idUsuario);

        if (mensaje.equals("Usuario eliminado satisfactoriamente.")) {
            session.setAttribute("mensaje", mensaje);
        } else {
            session.setAttribute("error", mensaje);
        }
    }

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
        String accion = request.getParameter("accion");
        Connection conn = (Connection) request.getSession().getAttribute("conexion");
        UsuarioDAO usuarioDAO = new UsuarioDAO(conn);

        if (accion == null) {
            response.sendRedirect("dashboard.jsp");
            return;
        }

        switch (accion) {
            case "crear":
                if (crearUsuario(request, usuarioDAO)) {
                    session.setAttribute("mensaje", "Usuario creado.");
                } else {
                    session.setAttribute("error", "Error al crear el usuario.");
                }
                break;
            case "modificar":
                if (modificarUsuario(request, usuarioDAO)) {
                    session.setAttribute("mensaje", "Usuario modificado satisfactoriamente.");
                } else {
                    session.setAttribute("error", "Error al modificar el usuario.");
                }
                ;
                break;
            case "eliminar":
                eliminarUsuario(request, session, usuarioDAO);
                break;
        }

        response.sendRedirect("dashboard.jsp");
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
