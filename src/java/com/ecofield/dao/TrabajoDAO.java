package com.ecofield.dao;

import com.ecofield.modelos.Trabajo;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrabajoDAO {

    private final Connection conn;

    public TrabajoDAO(Connection conn) {
        this.conn = conn;
    }

    // Obtener trabajos pendientes para un maquinista
    public List<Trabajo> obtenerTrabajosPendientes(int idMaquinista) throws SQLException {
        List<Trabajo> trabajos = new ArrayList<>();
        String sql = "SELECT t.ID_Trabajo, tt.ID_Tipo_Trabajo, t.Num_Parcela, t.Estado " +
                     "FROM trabajos t JOIN tipo_trabajo tt ON t.Tipo = tt.ID_Tipo_Trabajo " +
                     "WHERE t.ID_Maquinista = ? AND t.Estado = 'Pendiente'";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMaquinista);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Trabajo trabajo = new Trabajo();
                    trabajo.setId(rs.getInt("ID_Trabajo"));
                    trabajo.setTipoTrabajo(rs.getInt("ID_Tipo_Trabajo"));
                    trabajo.setNumParcela(rs.getInt("Num_Parcela"));
                    trabajo.setEstado(rs.getString("Estado"));
                    trabajos.add(trabajo);
                }
            }
        }
        return trabajos;
    }

    // Obtener trabajos en curso para un maquinista
    public List<Trabajo> obtenerTrabajosEnCurso(int idMaquinista) throws SQLException {
        List<Trabajo> trabajos = new ArrayList<>();
        String sql = "SELECT t.ID_Trabajo, tt.ID_Tipo_Trabajo, t.Num_Parcela, t.Estado " +
                     "FROM trabajos t JOIN tipo_trabajo tt ON t.Tipo = tt.ID_Tipo_Trabajo " +
                     "WHERE t.ID_Maquinista = ? AND t.Estado = 'En curso'";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMaquinista);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Trabajo trabajo = new Trabajo();
                    trabajo.setId(rs.getInt("ID_Trabajo"));
                    trabajo.setTipoTrabajo(rs.getInt("ID_Tipo_Trabajo"));
                    trabajo.setNumParcela(rs.getInt("Num_Parcela"));
                    trabajo.setEstado(rs.getString("Estado"));
                    trabajos.add(trabajo);
                }
            }
        }
        return trabajos;
    }

    // Obtener historial de trabajos finalizados para un maquinista
    public List<Trabajo> obtenerHistorialTrabajos(int idMaquinista) throws SQLException {
        List<Trabajo> trabajos = new ArrayList<>();
        String sql = "SELECT t.ID_Trabajo, tt.ID_Tipo_Trabajo, t.Num_Parcela, t.Estado, " +
                     "t.Fec_Inicio, t.Fec_Fin, t.Horas " +
                     "FROM trabajos t JOIN tipo_trabajo tt ON t.Tipo = tt.ID_Tipo_Trabajo " +
                     "WHERE t.ID_Maquinista = ? AND t.Estado = 'Finalizado'";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMaquinista);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Trabajo trabajo = new Trabajo();
                    trabajo.setId(rs.getInt("ID_Trabajo"));
                    trabajo.setTipoTrabajo(rs.getInt("ID_Tipo_Trabajo"));
                    trabajo.setNumParcela(rs.getInt("Num_Parcela"));
                    trabajo.setEstado(rs.getString("Estado"));
                    trabajo.setFecInicio(rs.getDate("Fec_Inicio"));
                    trabajo.setFecFin(rs.getDate("Fec_Fin"));
                    trabajo.setHoras(rs.getInt("Horas"));
                    trabajos.add(trabajo);
                }
            }
        }
        return trabajos;
    }

    // Obtener trabajos aprobados para un agricultor (finalizados) con filtros opcionales
    public List<Trabajo> obtenerTrabajosAprobadosPorPropietario(int idPropietario, Integer filtroTipoTrabajo, String filtroFechaInicio, String filtroFechaFin) {
        List<Trabajo> trabajos = new ArrayList<>();
        String sql = "SELECT t.ID_Trabajo, t.Num_parcela, t.ID_Maquina, t.ID_Maquinista, t.Fec_Inicio, t.Fec_Fin, t.Horas, " +
                     "t.Tipo AS Tipo_Trabajo, t.Estado " +
                     "FROM trabajos t JOIN parcelas p ON t.Num_parcela = p.Num_Parcela " +
                     "WHERE p.Propietario = ? AND t.Estado = 'Finalizado'";
        List<Object> parametros = new ArrayList<>();
        parametros.add(idPropietario);
        if (filtroTipoTrabajo != null) {
            sql += " AND t.Tipo = ?";
            parametros.add(filtroTipoTrabajo);
        }
        if (filtroFechaInicio != null && !filtroFechaInicio.isEmpty()) {
            sql += " AND t.Fec_Inicio >= ?";
            parametros.add(Date.valueOf(filtroFechaInicio)); // Se espera el formato YYYY-MM-DD
        }
        if (filtroFechaFin != null && !filtroFechaFin.isEmpty()) {
            sql += " AND t.Fec_Fin <= ?";
            parametros.add(Date.valueOf(filtroFechaFin));
        }
        sql += " ORDER BY t.Fec_Inicio DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < parametros.size(); i++) {
                stmt.setObject(i + 1, parametros.get(i));
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Trabajo trabajo = new Trabajo();
                    trabajo.setId(rs.getInt("ID_Trabajo"));
                    trabajo.setNumParcela(rs.getInt("Num_parcela"));
                    trabajo.setIdMaquina(rs.getInt("ID_Maquina"));
                    trabajo.setIdMaquinista(rs.getInt("ID_Maquinista"));
                    trabajo.setFecInicio(rs.getDate("Fec_Inicio"));
                    trabajo.setFecFin(rs.getDate("Fec_Fin"));
                    trabajo.setHoras(rs.getInt("Horas"));
                    trabajo.setTipoTrabajo(rs.getInt("Tipo_Trabajo"));
                    trabajo.setEstado(rs.getString("Estado"));
                    trabajos.add(trabajo);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrabajoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return trabajos;
    }

    // Iniciar trabajo: actualizar el estado a "En curso" y asignar la fecha de inicio
    public boolean iniciarTrabajo(int idTrabajo, Date fechaInicio, int idMaquinista) throws SQLException {
        String sql = "UPDATE trabajos SET Estado = 'En curso', Fec_Inicio = ? WHERE ID_Trabajo = ? AND ID_Maquinista = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, fechaInicio);
            stmt.setInt(2, idTrabajo);
            stmt.setInt(3, idMaquinista);
            return stmt.executeUpdate() > 0;
        }
    }

    // Finalizar trabajo: actualizar el estado a "Finalizado", la fecha de fin y las horas trabajadas.
    // Además, libera la máquina y genera una factura.
    public boolean finalizarTrabajo(int idTrabajo, Date fechaFin, double horas, int idMaquinista) throws SQLException {
        conn.setAutoCommit(false);
        try {
            // Actualizar el trabajo
            String sql = "UPDATE trabajos SET Estado = 'Finalizado', Fec_Fin = ?, Horas = ? WHERE ID_Trabajo = ? AND ID_Maquinista = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setDate(1, fechaFin);
                stmt.setDouble(2, horas);
                stmt.setInt(3, idTrabajo);
                stmt.setInt(4, idMaquinista);
                stmt.executeUpdate();
            }
            // Obtener ID de la máquina asignada al trabajo
            String sqlMaquina = "SELECT ID_Maquina FROM trabajos WHERE ID_Trabajo = ?";
            int idMaquina = 0;
            try (PreparedStatement stmt = conn.prepareStatement(sqlMaquina)) {
                stmt.setInt(1, idTrabajo);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        idMaquina = rs.getInt("ID_Maquina");
                    }
                }
            }
            // Actualizar el estado de la máquina a 'Disponible'
            String sqlUpdateMaquina = "UPDATE maquinas SET Estado = 'Disponible' WHERE ID_Maquina = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlUpdateMaquina)) {
                stmt.setInt(1, idMaquina);
                stmt.executeUpdate();
            }
            // Insertar una factura relacionada al trabajo
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

    // Obtener trabajos filtrados según criterios: agricultor, tipo de trabajo y orden de fecha.
    public List<Trabajo> obtenerTrabajosFiltrados(Integer agricultorFiltro, Integer tipoTrabajoFiltro, String ordenFecha) {
        List<Trabajo> trabajos = new ArrayList<>();
        String sql = "SELECT t.ID_Trabajo, t.Num_parcela, t.ID_Maquina, t.ID_Maquinista, t.Fec_inicio, t.Fec_fin, t.Horas, " +
                     "t.Tipo AS Tipo_Trabajo, t.Estado, p.Propietario " +
                     "FROM trabajos t JOIN parcelas p ON t.Num_parcela = p.Num_Parcela " +
                     "WHERE 1=1";
        List<Object> parametros = new ArrayList<>();
        if (agricultorFiltro != null) {
            sql += " AND p.Propietario = ?";
            parametros.add(agricultorFiltro);
        }
        if (tipoTrabajoFiltro != null) {
            sql += " AND t.Tipo = ?";
            parametros.add(tipoTrabajoFiltro);
        }
        sql += " ORDER BY t.Fec_inicio " + ordenFecha;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            int index = 1;
            for (Object param : parametros) {
                stmt.setObject(index++, param);
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Trabajo trabajo = new Trabajo(
                        rs.getInt("ID_Trabajo"),
                        rs.getInt("Num_parcela"),
                        rs.getInt("ID_Maquina"),
                        rs.getInt("ID_Maquinista"),
                        rs.getInt("Propietario"),
                        rs.getDate("Fec_inicio"),
                        rs.getDate("Fec_fin"),
                        rs.getInt("Horas"),
                        rs.getInt("Tipo_Trabajo"),
                        rs.getString("Estado")
                    );
                    trabajos.add(trabajo);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrabajoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return trabajos;
    }
}
