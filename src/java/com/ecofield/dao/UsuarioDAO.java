/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofield.dao;

import com.ecofield.modelos.Rol;
import com.ecofield.modelos.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Eduardo Olalde
 */
public class UsuarioDAO {

    private final Connection conn;

    public UsuarioDAO(Connection conn) {
        this.conn = conn;
    }

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
                            rs.getInt("Telefono"),
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

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }

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
                            rs.getInt("Telefono"),
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

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }

    public boolean actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE Usuarios SET Nombre = ?, Contrasenia = ?, Telefono = ?, Email = ? WHERE ID_Usuario = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getContrasenia());
            stmt.setInt(3, usuario.getTelefono());
            stmt.setString(4, usuario.getEmail());
            stmt.setInt(5, usuario.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean registrarUsuario(String nombre, String contrasenia, int telefono, String email) {
        String sql = "INSERT INTO Usuarios (Nombre, Contrasenia, Telefono, Email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setString(2, contrasenia);
            stmt.setInt(3, telefono);
            stmt.setString(4, email);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
