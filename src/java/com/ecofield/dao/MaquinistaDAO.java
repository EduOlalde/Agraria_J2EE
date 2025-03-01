/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofield.dao;

import com.ecofield.modelos.Maquinista;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase DAO para gestionar las operaciones relacionadas con los maquinistas en la base de datos.
 * Realiza operaciones como obtener, actualizar especialidades de los maquinistas y obtener la lista de maquinistas habilitados.
 * @author Eduardo Olalde
 */
public class MaquinistaDAO {

    private final Connection conn;

    /**
     * Constructor de la clase MaquinistaDAO
     * @param conn Conexión a la base de datos.
     */
    public MaquinistaDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Obtiene todos los maquinistas habilitados desde la base de datos.
     * @return Lista de maquinistas habilitados.
     */
    public List<Maquinista> obtenerMaquinistas() {
        List<Maquinista> maquinistas = new ArrayList<>();
        String sql = "SELECT u.ID_Usuario, u.Nombre, u.Email FROM usuarios u "
                + "JOIN usuarios_roles ur ON u.ID_Usuario = ur.ID_Usuario "
                + "JOIN roles r ON ur.ID_Rol = r.ID_Rol "
                + "WHERE r.Nombre = 'Maquinista' AND u.Habilitado = 1";
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Maquinista maquinista = new Maquinista();
                maquinista.setId(rs.getInt("ID_Usuario"));
                maquinista.setNombre(rs.getString("Nombre"));
                maquinista.setEmail(rs.getString("Email"));

                List<Integer> especialidades = obtenerEspecialidades(maquinista.getId());
                maquinista.setEspecialidades(especialidades);

                maquinistas.add(maquinista);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MaquinistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maquinistas;
    }

    /**
     * Obtiene las especialidades asociadas a un maquinista.
     * @param idMaquinista El ID del maquinista.
     * @return Lista de IDs de tipos de trabajo que representan las especialidades del maquinista.
     */
    public List<Integer> obtenerEspecialidades(int idMaquinista) {
        List<Integer> especialidades = new ArrayList<>();
        String sql = "SELECT ID_Tipo_Trabajo FROM maquinista_tipo_trabajo WHERE ID_Maquinista = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMaquinista);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    especialidades.add(rs.getInt("ID_Tipo_Trabajo"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MaquinistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return especialidades;
    }

    /**
     * Actualiza las especialidades de un maquinista. Primero elimina las especialidades no seleccionadas y luego inserta las nuevas especialidades.
     * @param idMaquinista El ID del maquinista a actualizar.
     * @param nuevasEspecialidades Lista de los nuevos tipos de trabajo que representarán las especialidades del maquinista.
     */
    public void actualizarEspecialidades(int idMaquinista, List<Integer> nuevasEspecialidades) {
        // Eliminar especialidades no seleccionadas
        String deleteSql = "DELETE FROM maquinista_tipo_trabajo WHERE ID_Maquinista = ? AND ID_Tipo_Trabajo NOT IN (?)";
        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
            deleteStmt.setInt(1, idMaquinista);
            deleteStmt.setString(2, String.join(",", nuevasEspecialidades.toString()));
            deleteStmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MaquinistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Insertar nuevas especialidades
        String insertSql = "INSERT INTO maquinista_tipo_trabajo (ID_Maquinista, ID_Tipo_Trabajo) VALUES (?, ?)";
        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
            for (Integer tipoTrabajoId : nuevasEspecialidades) {
                insertStmt.setInt(1, idMaquinista);
                insertStmt.setInt(2, tipoTrabajoId);
                insertStmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MaquinistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
