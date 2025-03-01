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

    public List<Trabajo> obtenerTrabajos(Integer agricultorFiltro, Integer tipoTrabajoFiltro, String estado, String ordenFecha,
            String filtroFechaInicio, String filtroFechaFin) {
        List<Trabajo> trabajos = new ArrayList<>();
        String sql = "SELECT t.ID_Trabajo, t.Num_parcela, t.ID_Maquina, t.ID_Maquinista, t.Fec_inicio, t.Fec_fin, t.Horas, "
                + "t.Tipo AS Tipo_Trabajo, t.Estado, p.Propietario "
                + "FROM trabajos t JOIN parcelas p ON t.Num_parcela = p.Num_Parcela "
                + "WHERE 1=1";  // La cláusula WHERE inicial que permite añadir más condiciones dinámicamente

        List<Object> parametros = new ArrayList<>();

        // Filtrar por agricultor (que es lo mismo que el propietario) si se proporciona
        if (agricultorFiltro != null) {
            sql += " AND p.Propietario = ?";
            parametros.add(agricultorFiltro);
        }

        // Filtrar por tipo de trabajo si se proporciona
        if (tipoTrabajoFiltro != null) {
            sql += " AND t.Tipo = ?";
            parametros.add(tipoTrabajoFiltro);
        }

        // Filtrar por estado si se proporciona
        if (estado != null && !estado.isEmpty()) {
            sql += " AND t.Estado = ?";
            parametros.add(estado);
        }

        // Filtrar por fechas si se proporcionan
        if (filtroFechaInicio != null && !filtroFechaInicio.isEmpty()) {
            sql += " AND t.Fec_Inicio >= ?";
            parametros.add(Date.valueOf(filtroFechaInicio)); // Se espera el formato YYYY-MM-DD
        }
        if (filtroFechaFin != null && !filtroFechaFin.isEmpty()) {
            sql += " AND t.Fec_Fin <= ?";
            parametros.add(Date.valueOf(filtroFechaFin)); // Se espera el formato YYYY-MM-DD
        }

        // Ordenar por fecha según lo indicado
        sql += " ORDER BY t.Fec_inicio " + (ordenFecha != null ? ordenFecha : "DESC");

        // Ejecutar la consulta
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
                            rs.getInt("Propietario"), // El agricultor es el propietario
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

    // Obtener trabajos por estado para un maquinista
    public List<Trabajo> obtenerTrabajosPorEstado(int idMaquinista, String estado) {
        List<Trabajo> trabajos = new ArrayList<>();
        String sql = "SELECT t.ID_Trabajo, tt.ID_Tipo_Trabajo, t.Num_Parcela, t.Estado, "
                + "t.Fec_Inicio, t.Fec_Fin, t.Horas "
                + "FROM trabajos t JOIN tipo_trabajo tt ON t.Tipo = tt.ID_Tipo_Trabajo "
                + "WHERE t.ID_Maquinista = ? AND t.Estado = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMaquinista);
            stmt.setString(2, estado);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Trabajo trabajo = new Trabajo();
                    trabajo.setId(rs.getInt("ID_Trabajo"));
                    trabajo.setTipoTrabajo(rs.getInt("ID_Tipo_Trabajo"));
                    trabajo.setNumParcela(rs.getInt("Num_Parcela"));
                    trabajo.setEstado(rs.getString("Estado"));

                    // Si el estado es 'Finalizado', también obtenemos las fechas y horas
                    if ("Finalizado".equals(estado)) {
                        trabajo.setFecInicio(rs.getDate("Fec_Inicio"));
                        trabajo.setFecFin(rs.getDate("Fec_Fin"));
                        trabajo.setHoras(rs.getInt("Horas"));
                    }

                    trabajos.add(trabajo);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrabajoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return trabajos;
    }

    public Trabajo obtenerTrabajoPorId(int idTrabajo) {
        Trabajo trabajo = null;
        String sql = "SELECT ID_Trabajo, Num_Parcela, ID_Maquina, ID_Maquinista, "
                + "Fec_Inicio, Fec_Fin, Horas, Tipo, Estado "
                + "FROM trabajos WHERE ID_Trabajo = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idTrabajo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    trabajo = new Trabajo();
                    trabajo.setId(rs.getInt("ID_Trabajo"));
                    trabajo.setNumParcela(rs.getInt("Num_Parcela"));
                    trabajo.setIdMaquina(rs.getInt("ID_Maquina"));
                    trabajo.setIdMaquinista(rs.getInt("ID_Maquinista"));
                    trabajo.setFecInicio(rs.getDate("Fec_Inicio"));
                    trabajo.setFecFin(rs.getDate("Fec_Fin"));
                    trabajo.setHoras(rs.getInt("Horas"));
                    trabajo.setTipoTrabajo(rs.getInt("Tipo"));
                    trabajo.setEstado(rs.getString("Estado"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrabajoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return trabajo;
    }

// Iniciar trabajo
    public boolean iniciarTrabajo(int idTrabajo, Date fechaInicio) {
        String sql = "UPDATE trabajos SET Estado = 'En curso', Fec_Inicio = ? WHERE ID_Trabajo = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, fechaInicio);
            stmt.setInt(2, idTrabajo);
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TrabajoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

// Finalizar trabajo
    public boolean finalizarTrabajo(int idTrabajo, Date fechaFin, double horas) {
        try {
            conn.setAutoCommit(false);
            try {
                // Actualizar estado de trabajo
                String sql = "UPDATE trabajos SET Estado = 'Finalizado', Fec_Fin = ?, Horas = ? WHERE ID_Trabajo = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setDate(1, fechaFin);
                    stmt.setDouble(2, horas);
                    stmt.setInt(3, idTrabajo);
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

                //Calcular monto
                String sqlExtension = "SELECT p.Extension FROM trabajos t JOIN parcelas p ON t.Num_parcela = p.Num_Parcela WHERE t.ID_Trabajo = ?";
                double monto = 0;
                try (PreparedStatement stmt = conn.prepareStatement(sqlExtension)) {
                    stmt.setInt(1, idTrabajo);
                    stmt.executeQuery();
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        double extension = rs.getDouble("Extension");
                        monto = 30 * horas * extension;
                    }
                }

                // Insertar factura                
                String sqlFactura = "INSERT INTO facturas (ID_Trabajo, Monto) VALUES (?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sqlFactura)) {
                    stmt.setInt(1, idTrabajo);
                    stmt.setDouble(2, monto);
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
        } catch (SQLException ex) {
            Logger.getLogger(TrabajoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
