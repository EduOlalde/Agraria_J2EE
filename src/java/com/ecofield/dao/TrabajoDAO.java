/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofield.dao;

import com.ecofield.modelos.Trabajo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Eduardo Olalde
 */
public class TrabajoDAO {

    private final Connection conn;

    public TrabajoDAO(Connection conn) {
        this.conn = conn;
    }

    // Obtener trabajos pendientes
    public List<Trabajo> obtenerTrabajosPendientes(int idMaquinista) throws SQLException {
        List<Trabajo> trabajos = new ArrayList<>();
        String sql = "SELECT t.ID_Trabajo, tt.ID_Tipo_Trabajo, t.Num_Parcela, t.Estado "
                + "FROM trabajos t JOIN tipo_trabajo tt ON t.Tipo = tt.ID_Tipo_Trabajo "
                + "WHERE t.ID_Maquinista = ? AND t.Estado = 'Pendiente'";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMaquinista);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Trabajo trabajo = new Trabajo();
                trabajo.setId(rs.getInt("ID_Trabajo"));

                // Obtener el ID del tipo de trabajo (ID_Tipo_Trabajo)
                int tipoTrabajoId = rs.getInt("ID_Tipo_Trabajo");
                trabajo.setTipoTrabajo(tipoTrabajoId);  // Asignar directamente el ID (int) al atributo

                trabajo.setNumParcela(rs.getInt("Num_Parcela"));
                trabajo.setEstado(rs.getString("Estado"));
                trabajos.add(trabajo);
            }
        }
        return trabajos;
    }

    // Obtener trabajos en curso
    public List<Trabajo> obtenerTrabajosEnCurso(int idMaquinista) throws SQLException {
        List<Trabajo> trabajos = new ArrayList<>();
        String sql = "SELECT t.ID_Trabajo, tt.ID_Tipo_Trabajo, t.Num_Parcela, t.Estado "
                + "FROM trabajos t JOIN tipo_trabajo tt ON t.Tipo = tt.ID_Tipo_Trabajo "
                + "WHERE t.ID_Maquinista = ? AND t.Estado = 'En curso'";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMaquinista);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Trabajo trabajo = new Trabajo();
                trabajo.setId(rs.getInt("ID_Trabajo"));
                trabajo.setTipoTrabajo(rs.getInt("ID_Tipo_Trabajo"));  // Asignación directa del int
                trabajo.setNumParcela(rs.getInt("Num_Parcela"));
                trabajo.setEstado(rs.getString("Estado"));
                trabajos.add(trabajo);
            }
        }
        return trabajos;
    }

    // Obtener historial de trabajos finalizados
    public List<Trabajo> obtenerHistorialTrabajos(int idMaquinista) throws SQLException {
        List<Trabajo> trabajos = new ArrayList<>();
        String sql = "SELECT t.ID_Trabajo, tt.ID_Tipo_Trabajo, t.Num_Parcela, t.Estado, "
                + "t.Fec_Inicio, t.Fec_Fin, t.Horas "
                + "FROM trabajos t JOIN tipo_trabajo tt ON t.Tipo = tt.ID_Tipo_Trabajo "
                + "WHERE t.ID_Maquinista = ? AND t.Estado = 'Finalizado'";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMaquinista);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Trabajo trabajo = new Trabajo();
                trabajo.setId(rs.getInt("ID_Trabajo"));
                trabajo.setTipoTrabajo(rs.getInt("ID_Tipo_Trabajo"));  // Asignación directa del int
                trabajo.setNumParcela(rs.getInt("Num_Parcela"));
                trabajo.setEstado(rs.getString("Estado"));
                trabajo.setFecInicio(rs.getDate("Fec_Inicio"));
                trabajo.setFecFin(rs.getDate("Fec_Fin"));
                trabajo.setHoras(rs.getInt("Horas"));
                trabajos.add(trabajo);
            }
        }
        return trabajos;
    }

    // Iniciar trabajo
    public boolean iniciarTrabajo(int idTrabajo, Date fechaInicio, int idMaquinista) throws SQLException {
        String sql = "UPDATE trabajos SET Estado = 'En curso', Fec_Inicio = ? WHERE ID_Trabajo = ? AND ID_Maquinista = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, fechaInicio);
            stmt.setInt(2, idTrabajo);
            stmt.setInt(3, idMaquinista);
            return stmt.executeUpdate() > 0;
        }
    }

    // Finalizar trabajo
    public boolean finalizarTrabajo(int idTrabajo, Date fechaFin, double horas, int idMaquinista) throws SQLException {
        conn.setAutoCommit(false);
        try {
            // Actualizar estado de trabajo
            String sql = "UPDATE trabajos SET Estado = 'Finalizado', Fec_Fin = ?, Horas = ? WHERE ID_Trabajo = ? AND ID_Maquinista = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setDate(1, fechaFin);
                stmt.setDouble(2, horas);
                stmt.setInt(3, idTrabajo);
                stmt.setInt(4, idMaquinista);
                stmt.executeUpdate();
            }

            // Obtener ID de la máquina
            String sqlMaquina = "SELECT ID_Maquina FROM trabajos WHERE ID_Trabajo = ?";
            int idMaquina = 0;
            try (PreparedStatement stmt = conn.prepareStatement(sqlMaquina)) {
                stmt.setInt(1, idTrabajo);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    idMaquina = rs.getInt("ID_Maquina");
                }
            }

            // Actualizar estado de la máquina
            String sqlUpdateMaquina = "UPDATE maquinas SET Estado = 'Disponible' WHERE ID_Maquina = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlUpdateMaquina)) {
                stmt.setInt(1, idMaquina);
                stmt.executeUpdate();
            }

            // Insertar factura
            String sqlFactura = "INSERT INTO facturas (ID_Trabajo) VALUES (?)";
            try (PreparedStatement stmt = conn.prepareStatement(sqlFactura)) {
                stmt.setInt(1, idTrabajo);
                stmt.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }
}
