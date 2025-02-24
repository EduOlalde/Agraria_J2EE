/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofield.dao;

import com.ecofield.enums.EstadoTrabajo;
import com.ecofield.modelos.Trabajo;
import com.ecofield.utils.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Eduardo Olalde
 */
public class TrabajoDAO {
    
    private Connection conn;

    public TrabajoDAO() throws SQLException {
        this.conn = ConexionDB.conectar();
    }

    private static final String SELECT_TRABAJOS_SOLICITADOS = "SELECT * FROM trabajos_solicitados WHERE Propietario = ? AND (Estado = 'En revision' OR Estado = 'Rechazado')";
    private static final String SELECT_TRABAJOS_APROBADOS = "SELECT * FROM trabajos WHERE Propietario = ? AND Estado = 'Aprobado'";

    public List<Trabajo> obtenerTrabajosSolicitados(int idUsuario, int tipoTrabajo, String fechaInicio, String fechaFin) throws SQLException {
        List<Trabajo> trabajos = new ArrayList<>();
        try (
             PreparedStatement stmt = conn.prepareStatement(SELECT_TRABAJOS_SOLICITADOS)) {
            stmt.setInt(1, idUsuario);
            // Aplica filtros si es necesario
            if (tipoTrabajo != 0) {
                stmt.setInt(2, tipoTrabajo);
            }
            if (fechaInicio != null) {
                stmt.setString(3, fechaInicio);
            }
            if (fechaFin != null) {
                stmt.setString(4, fechaFin);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                trabajos.add(mapRowToTrabajo(rs));
            }
        }
        return trabajos;
    }

    public List<Trabajo> obtenerTrabajosAprobados(int idUsuario, int tipoTrabajo, String fechaInicio, String fechaFin) throws SQLException {
        List<Trabajo> trabajos = new ArrayList<>();
        try (
             PreparedStatement stmt = conn.prepareStatement(SELECT_TRABAJOS_APROBADOS)) {
            stmt.setInt(1, idUsuario);
            // Aplica filtros si es necesario
            if (tipoTrabajo != 0) {
                stmt.setInt(2, tipoTrabajo);
            }
            if (fechaInicio != null) {
                stmt.setString(3, fechaInicio);
            }
            if (fechaFin != null) {
                stmt.setString(4, fechaFin);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                trabajos.add(mapRowToTrabajo(rs));
            }
        }
        return trabajos;
    }

    private Trabajo mapRowToTrabajo(ResultSet rs) throws SQLException {
        int id = rs.getInt("ID_Trabajo");
        int numParcela = rs.getInt("Num_Parcela");
        int idMaquina = rs.getInt("ID_Maquina");
        int idMaquinista = rs.getInt("ID_Maquinista");
        Date fecInicio = rs.getDate("Fec_inicio");
        Date fecFin = rs.getDate("Fec_fin");
        int horas = rs.getInt("Horas");
        int tipoTrabajo = rs.getInt("ID_Tipo_Trabajo");
        EstadoTrabajo estado = EstadoTrabajo.valueOf(rs.getString("Estado").toUpperCase());
        
        return new Trabajo(id, numParcela, idMaquina, idMaquinista, fecInicio, fecFin, horas, tipoTrabajo, estado);
    }
}
