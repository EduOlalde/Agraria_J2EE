/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofield.dao;

import com.ecofield.modelos.Maquina;
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
public class MaquinaDAO {

    private final Connection conn;

    public MaquinaDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean agregarMaquina(Maquina maquina) {
        String sql = "INSERT INTO maquinas (Estado, Tipo_Maquina, Modelo) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maquina.getEstado());
            stmt.setInt(2, maquina.getTipoMaquina());
            stmt.setString(3, maquina.getModelo());
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(MaquinaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean actualizarEstadoMaquina(int idMaquina, String nuevoEstado) {
        String sql = "UPDATE maquinas SET Estado = ? WHERE ID_Maquina = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, idMaquina);
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(MaquinaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean eliminarMaquina(int idMaquina) {
        String sql = "DELETE FROM maquinas WHERE ID_Maquina = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMaquina);
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(MaquinaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Maquina> obtenerMaquinas(String filtroEstado, Integer filtroTipoMaquina) {
        List<Maquina> maquinas = new ArrayList<>();
        String sql = "SELECT m.ID_Maquina, m.Estado, m.Tipo_Maquina, m.Modelo "
                + "FROM maquinas m WHERE 1";
        if (filtroEstado != null) {
            sql += " AND m.Estado = ?";
        }
        if (filtroTipoMaquina != null) {
            sql += " AND m.Tipo_Maquina = ?";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            int index = 1;
            if (filtroEstado != null) {
                stmt.setString(index++, filtroEstado);
            }
            if (filtroTipoMaquina != null) {
                stmt.setInt(index++, filtroTipoMaquina);
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Maquina maquina = new Maquina(
                        rs.getInt("ID_Maquina"),
                        rs.getString("Estado"),
                        rs.getInt("Tipo_Maquina"),
                        rs.getString("Modelo")
                );
                maquinas.add(maquina);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MaquinaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maquinas;
    }

    public List<Maquina> getMaquinasDisponibles() {
        List<Maquina> maquinas = new ArrayList<>();
        String sql = "SELECT m.ID_Maquina, m.Estado, m.Tipo_Maquina, m.Modelo "
                + "FROM maquinas m "
                + "WHERE m.Estado = 'Disponible'";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Maquina maquina = new Maquina(
                        rs.getInt("ID_Maquina"),
                        rs.getString("Estado"),
                        rs.getInt("Tipo_Maquina"),
                        rs.getString("Modelo")
                );
                maquinas.add(maquina);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MaquinaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maquinas;
    }

    public List<Maquina> getMaquinasDisponiblesPorTipoTrabajo(int idTipoTrabajo) {
        List<Maquina> maquinas = new ArrayList<>();
        String sql = "SELECT m.ID_Maquina, m.Estado, m.Tipo_Maquina, m.Modelo "
                + "FROM maquinas m "
                + "JOIN maquinista_maquina mm ON m.ID_Maquina = mm.ID_Maquina "
                + "JOIN maquinista_tipo_trabajo mt ON mm.ID_Maquinista = mt.ID_Maquinista "
                + "WHERE m.Estado = 'Disponible' AND mt.ID_Tipo_Trabajo = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTipoTrabajo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Maquina maquina = new Maquina(
                        rs.getInt("ID_Maquina"),
                        rs.getString("Estado"),
                        rs.getInt("Tipo_Maquina"),
                        rs.getString("Modelo")
                );
                maquinas.add(maquina);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MaquinaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maquinas;
    }
}
