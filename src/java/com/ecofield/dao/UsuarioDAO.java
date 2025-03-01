package com.ecofield.dao;

import com.ecofield.modelos.Rol;
import com.ecofield.modelos.Usuario;
import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase DAO para gestionar las operaciones con la tabla Usuarios.
 */
public class UsuarioDAO {

    private final Connection conn;

    public UsuarioDAO(Connection conn) {
        this.conn = conn;
    }

    // Obtener un usuario por ID
    public Usuario obtenerUsuarioPorId(int id) {
        Usuario usuario = null;
        String sql = "SELECT u.*, r.ID_Rol, r.Nombre AS RolNombre FROM Usuarios u "
                + "LEFT JOIN usuarios_roles ur ON u.ID_Usuario = ur.ID_Usuario "
                + "LEFT JOIN roles r ON ur.ID_Rol = r.ID_Rol "
                + "WHERE u.ID_Usuario = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            List<Rol> roles = new ArrayList<>();

            while (rs.next()) {
                if (usuario == null) {
                    usuario = new Usuario(
                            rs.getInt("ID_Usuario"),
                            rs.getString("Nombre"),
                            rs.getString("Email"),
                            rs.getString("Contrasenia"),
                            rs.getString("Telefono"),
                            rs.getBoolean("Habilitado")
                    );
                }

                if (rs.getInt("ID_Rol") != 0) {
                    roles.add(new Rol(rs.getInt("ID_Rol"), rs.getString("RolNombre")));
                }
            }

            if (usuario != null) {
                usuario.setRoles(roles);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuario;
    }

    // Obtener un usuario por nombre
    public Usuario obtenerUsuarioPorNombre(String nombre) {
        Usuario usuario = null;
        String sql = "SELECT u.*, r.ID_Rol, r.Nombre AS RolNombre FROM Usuarios u "
                + "LEFT JOIN usuarios_roles ur ON u.ID_Usuario = ur.ID_Usuario "
                + "LEFT JOIN roles r ON ur.ID_Rol = r.ID_Rol "
                + "WHERE u.Nombre = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            List<Rol> roles = new ArrayList<>();

            while (rs.next()) {
                if (usuario == null) {
                    usuario = new Usuario(
                            rs.getInt("ID_Usuario"),
                            rs.getString("Nombre"),
                            rs.getString("Email"),
                            rs.getString("Contrasenia"),
                            rs.getString("Telefono"), // Cambié a String para manejar caracteres especiales
                            rs.getBoolean("Habilitado")
                    );
                }

                if (rs.getInt("ID_Rol") != 0) {
                    roles.add(new Rol(rs.getInt("ID_Rol"), rs.getString("RolNombre")));
                }
            }

            if (usuario != null) {
                usuario.setRoles(roles);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuario;
    }

    public List<Usuario> obtenerUsuariosPorRol(String rol) {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.ID_Usuario, u.Nombre FROM usuarios u "
                + "JOIN usuarios_roles ur ON u.ID_Usuario = ur.ID_Usuario "
                + "JOIN roles r ON ur.ID_Rol = r.ID_Rol WHERE r.Nombre = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Se establece el parámetro para el nombre del rol
            stmt.setString(1, rol);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Usuario usuario = new Usuario(rs.getInt("ID_Usuario"), rs.getString("Nombre"));
                    usuarios.add(usuario);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuarios;
    }

    // Actualizar los datos de un usuario
    public String actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE Usuarios SET Nombre = ?, Contrasenia = ?, Telefono = ?, Email = ? WHERE ID_Usuario = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getContrasenia());
            stmt.setString(3, usuario.getTelefono());
            stmt.setString(4, usuario.getEmail());
            stmt.setInt(5, usuario.getId());

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                return "Usuario actualizado correctamente.";
            } else {
                return "No se encontró el usuario o no se realizaron cambios.";
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            String mensajeError = e.getMessage();

            if (mensajeError.contains("Email")) {
                return "Error: El email ingresado ya está registrado por otro usuario.";
            } else if (mensajeError.contains("Nombre")) {
                return "Error: El nombre de usuario ya está en uso.";
            } else {
                return "Error: Restricción de integridad violada.";
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "Error en la base de datos al actualizar el usuario.";
        }
    }

    // Registrar un nuevo usuario
    public String registrarUsuario(String nombre, String contrasenia, String telefono, String email) {
        String sql = "INSERT INTO Usuarios (Nombre, Contrasenia, Telefono, Email) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, contrasenia);
            stmt.setString(3, telefono);
            stmt.setString(4, email);

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                return "Usuario registrado correctamente.";
            } else {
                return "No se pudo registrar el usuario.";
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            String mensajeError = e.getMessage();

            if (mensajeError.contains("Email")) {
                return "Error: El email ingresado ya está registrado.";
            } else if (mensajeError.contains("Nombre")) {
                return "Error: El nombre de usuario ya está en uso.";
            } else {
                return "Error: Restricción de integridad violada.";
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "Error en la base de datos al registrar el usuario.";
        }
    }

    // Listar todos los usuarios con sus roles
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.id_usuario, u.nombre, u.email, u.telefono, u.habilitado, r.id_rol, r.nombre AS rol_nombre "
                + "FROM usuarios u "
                + "LEFT JOIN usuarios_roles ur ON u.id_usuario = ur.id_usuario "
                + "LEFT JOIN roles r ON ur.id_rol = r.id_rol "
                + "ORDER BY u.id_usuario";

        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            Usuario usuarioActual = null;
            int idUsuarioActual = -1;

            while (rs.next()) {
                int idUsuario = rs.getInt("id_usuario");

                if (usuarioActual == null || idUsuarioActual != idUsuario) {
                    usuarioActual = new Usuario(
                            idUsuario,
                            rs.getString("nombre"),
                            rs.getString("email"),
                            "", // Contrasenia vacía, no se trae
                            rs.getString("telefono"),
                            rs.getBoolean("habilitado")
                    );
                    usuarioActual.setRoles(new ArrayList<>());
                    usuarios.add(usuarioActual);
                    idUsuarioActual = idUsuario;
                }

                if (rs.getInt("id_rol") != 0) {
                    usuarioActual.getRoles().add(new Rol(rs.getInt("id_rol"), rs.getString("rol_nombre")));
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuarios;
    }

    // Registrar un nuevo usuario con roles
    public String registrarUsuario(Usuario usuario, List<Rol> roles) {
        String sqlUsuario = "INSERT INTO usuarios (nombre, email, telefono, contrasenia, habilitado) VALUES (?, ?, ?, ?, ?)";
        String sqlRol = "INSERT INTO usuarios_roles (id_usuario, id_rol) VALUES (?, ?)";

        try (PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
            stmtUsuario.setString(1, usuario.getNombre());
            stmtUsuario.setString(2, usuario.getEmail());
            stmtUsuario.setString(3, usuario.getTelefono());
            stmtUsuario.setString(4, usuario.getContrasenia());
            stmtUsuario.setBoolean(5, usuario.isHabilitado());

            int filasAfectadas = stmtUsuario.executeUpdate();
            if (filasAfectadas == 0) {
                return "No se pudo registrar el usuario.";
            }

            // Obtener el ID generado para el usuario
            ResultSet rs = stmtUsuario.getGeneratedKeys();
            if (rs.next()) {
                int idUsuario = rs.getInt(1);
                try (PreparedStatement stmtRol = conn.prepareStatement(sqlRol)) {
                    for (Rol rol : roles) {
                        stmtRol.setInt(1, idUsuario);
                        stmtRol.setInt(2, rol.getIdRol());
                        stmtRol.executeUpdate();
                    }
                }
                return "Usuario registrado correctamente.";
            } else {
                return "Error al obtener el ID del nuevo usuario.";
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            String mensajeError = e.getMessage();

            if (mensajeError.contains("Email")) {
                return "Error: El email ingresado ya está registrado.";
            } else if (mensajeError.contains("Nombre")) {
                return "Error: El nombre de usuario ya está en uso.";
            } else {
                return "Error: Restricción de integridad violada.";
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "Error en la base de datos al registrar el usuario.";
        }
    }

    // Actualizar usuario y roles
    public String actualizarUsuario(Usuario usuario, List<Rol> roles) {
        // Verificar si es el usuario admin antes de modificar
        if (esAdmin(usuario.getId())) {
            boolean sigueSiendoAdmin = roles.stream().anyMatch(rol -> rol.getIdRol() == 1);
            if (!sigueSiendoAdmin) {
                return "No se puede quitar el rol de Administrador al administrador.";
            }
            if (!usuario.isHabilitado()) {
                return "No se puede deshabilitar al administrador.";
            }
        }

        String sqlUsuario = "UPDATE usuarios SET nombre=?, email=?, telefono=?, habilitado=? WHERE id_usuario=?";
        String sqlEliminarRoles = "DELETE FROM usuarios_roles WHERE id_usuario=?";
        String sqlInsertRol = "INSERT INTO usuarios_roles (id_usuario, id_rol) VALUES (?, ?)";

        try (PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario)) {

            stmtUsuario.setString(1, usuario.getNombre());
            stmtUsuario.setString(2, usuario.getEmail());
            stmtUsuario.setString(3, usuario.getTelefono());
            stmtUsuario.setBoolean(4, usuario.isHabilitado());
            stmtUsuario.setInt(5, usuario.getId());

            int filasAfectadas = stmtUsuario.executeUpdate();
            if (filasAfectadas == 0) {
                return "No se pudo actualizar el usuario. Puede que el ID no exista.";
            }

            try (PreparedStatement stmtEliminarRoles = conn.prepareStatement(sqlEliminarRoles)) {
                stmtEliminarRoles.setInt(1, usuario.getId());
                stmtEliminarRoles.executeUpdate();
            }

            try (PreparedStatement stmtInsertRol = conn.prepareStatement(sqlInsertRol)) {
                for (Rol rol : roles) {
                    stmtInsertRol.setInt(1, usuario.getId());
                    stmtInsertRol.setInt(2, rol.getIdRol());
                    stmtInsertRol.executeUpdate();
                }
            }

            return "Usuario actualizado correctamente.";

        } catch (SQLIntegrityConstraintViolationException e) {
            return "Error: El email ingresado ya está registrado en otro usuario.";

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "Error en la base de datos al actualizar el usuario.";
        }
    }

    // Eliminar usuario (evitando eliminar admin)
    public String eliminarUsuario(int id) {
        if (esAdmin(id)) {
            return "No se puede eliminar al administrador.";
        }

        if (tieneTrabajosAsignados(id)) {
            return "No se puede eliminar al usuario porque tiene trabajos asignados.";
        }

        return ejecutarEliminacion(id);
    }

    private boolean esAdmin(int id) {
        return id == 1;
    }

    private boolean tieneTrabajosAsignados(int id) {
        String sql = "SELECT COUNT(*) FROM trabajos WHERE id_maquinista = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private String ejecutarEliminacion(int id) {
        try (PreparedStatement stmtRoles = conn.prepareStatement("DELETE FROM usuarios_roles WHERE id_usuario = ?")) {
            stmtRoles.setInt(1, id);
            stmtRoles.executeUpdate();

            try (PreparedStatement stmtUsuario = conn.prepareStatement("DELETE FROM usuarios WHERE id_usuario = ?")) {
                stmtUsuario.setInt(1, id);
                if (stmtUsuario.executeUpdate() > 0) {
                    return "Usuario eliminado correctamente.";
                } else {
                    return "Error al eliminar el usuario, puede que no exista.";
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "Error en la base de datos al eliminar el usuario.";
        }
    }

}
