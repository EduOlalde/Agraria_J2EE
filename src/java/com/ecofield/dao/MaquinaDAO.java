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
 * Clase DAO que gestiona las operaciones CRUD para las máquinas en la base de datos.
 * Proporciona métodos para agregar, actualizar, eliminar y consultar máquinas,
 * además de obtener máquinas disponibles, tanto generales como filtradas por tipo de trabajo.
 * 
 * @author Eduardo Olalde
 */
public class MaquinaDAO {

    private final Connection conn;

    /**
     * Constructor de la clase MaquinaDAO.
     * 
     * @param conn La conexión a la base de datos que será utilizada para las consultas.
     */
    public MaquinaDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Agrega una nueva máquina a la base de datos.
     * 
     * @param maquina La máquina a agregar.
     * @return true si la máquina fue agregada correctamente, false en caso contrario.
     */
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

    /**
     * Actualiza el estado de una máquina en la base de datos.
     * 
     * @param idMaquina El ID de la máquina cuya información se actualizará.
     * @param nuevoEstado El nuevo estado que se asignará a la máquina.
     * @return true si el estado fue actualizado correctamente, false en caso contrario.
     */
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

    /**
     * Elimina una máquina de la base de datos.
     * 
     * @param idMaquina El ID de la máquina a eliminar.
     * @return true si la máquina fue eliminada correctamente, false en caso contrario.
     */
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

    /**
     * Obtiene una lista de máquinas filtradas por estado y tipo de máquina.
     * 
     * @param filtroEstado El estado por el cual filtrar las máquinas (puede ser null).
     * @param filtroTipoMaquina El tipo de máquina por el cual filtrar (puede ser null).
     * @return Una lista de máquinas que cumplen con los filtros especificados.
     */
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

    /**
     * Obtiene una lista de máquinas que están disponibles.
     * 
     * @return Una lista de máquinas que tienen el estado 'Disponible'.
     */
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

    /**
     * Obtiene una lista de máquinas disponibles para un tipo de trabajo específico.
     * 
     * @param idTipoTrabajo El ID del tipo de trabajo para filtrar las máquinas.
     * @return Una lista de máquinas disponibles para el tipo de trabajo especificado.
     */
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
