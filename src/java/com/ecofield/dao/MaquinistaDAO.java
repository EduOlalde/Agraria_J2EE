/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofield.dao;

import com.ecofield.modelos.Maquinista;
import com.ecofield.modelos.TipoTrabajo;
import com.ecofield.modelos.Usuario;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eduardo Olalde
 */
public class MaquinistaDAO {

    private final Connection conn;

    public MaquinistaDAO(Connection conn) {
        this.conn = conn;
    }

    // Obtener todos los maquinistas habilitados
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
    // Obtener las especialidades de un maquinista
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

    // Obtener todos los tipos de trabajo
    public List<TipoTrabajo> obtenerTiposTrabajo() {
        List<TipoTrabajo> tiposTrabajo = new ArrayList<>();
        String sql = "SELECT * FROM tipo_trabajo";

        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                TipoTrabajo tipoTrabajo = new TipoTrabajo(
                        rs.getInt("ID_Tipo_Trabajo"),
                        rs.getString("Nombre"));
                tiposTrabajo.add(tipoTrabajo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MaquinistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tiposTrabajo;
    }

    // Actualizar las especialidades de un maquinista
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
