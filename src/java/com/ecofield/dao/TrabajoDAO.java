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

/**
 * Esta clase se encarga de interactuar con la base de datos para realizar operaciones relacionadas
 * con los trabajos registrados en el sistema, como obtener, iniciar, finalizar trabajos, y realizar filtros.
 */
public class TrabajoDAO {

    private final Connection conn;

    /**
     * Constructor de la clase TrabajoDAO.
     * 
     * @param conn Conexión a la base de datos.
     */
    public TrabajoDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Obtiene una lista de trabajos basados en varios filtros, como agricultor, tipo de trabajo, estado, fechas y orden de fecha.
     * 
     * @param agricultorFiltro Filtro por el agricultor (propietario de la parcela).
     * @param tipoTrabajoFiltro Filtro por tipo de trabajo.
     * @param estado Filtro por estado del trabajo.
     * @param ordenFecha Orden en que se deben mostrar los trabajos por fecha (ASC/DESC).
     * @param filtroFechaInicio Filtro por fecha de inicio.
     * @param filtroFechaFin Filtro por fecha de finalización.
     * @return Lista de trabajos que cumplen con los filtros proporcionados.
     */
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

    /**
     * Obtiene una lista de trabajos basados en el estado para un maquinista específico.
     * 
     * @param idMaquinista ID del maquinista.
     * @param estado Estado de los trabajos a filtrar.
     * @return Lista de trabajos asociados al maquinista y con el estado especificado.
     */
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

    /**
     * Obtiene un trabajo específico a partir de su ID.
     * 
     * @param idTrabajo ID del trabajo a obtener.
     * @return El trabajo correspondiente al ID proporcionado, o null si no se encuentra.
     */
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

    /**
     * Inicia un trabajo, cambiando su estado a "En curso" y estableciendo la fecha de inicio.
     * 
     * @param idTrabajo ID del trabajo que se desea iniciar.
     * @param fechaInicio Fecha en la que comienza el trabajo.
     * @return true si el trabajo fue iniciado correctamente, false en caso contrario.
     */
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

    /**
     * Finaliza un trabajo, actualizando su estado a "Finalizado", registrando la fecha de fin y las horas trabajadas.
     * También actualiza el estado de la máquina y genera una factura con el monto calculado.
     * 
     * @param idTrabajo ID del trabajo que se desea finalizar.
     * @param fechaFin Fecha en la que finaliza el trabajo.
     * @param horas Cantidad de horas trabajadas.
     * @return true si el trabajo fue finalizado correctamente, false en caso contrario.
     */
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

                // Generar factura
                double montoFactura = horas * obtenerPrecioPorHoraMaquina(idMaquina);
                generarFactura(idTrabajo, montoFactura);

                conn.commit();
                return true;
            } catch (SQLException ex) {
                conn.rollback();
                Logger.getLogger(TrabajoDAO.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrabajoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Obtiene el precio por hora de la máquina según su ID.
     * 
     * @param idMaquina ID de la máquina.
     * @return El precio por hora de la máquina.
     */
    private double obtenerPrecioPorHoraMaquina(int idMaquina) {
        // Implementar la consulta para obtener el precio por hora de la máquina
        return 50.0;  // Este valor es un ejemplo
    }

    /**
     * Genera una factura para el trabajo con el monto especificado.
     * 
     * @param idTrabajo ID del trabajo para el que se genera la factura.
     * @param montoFactura Monto de la factura.
     */
    private void generarFactura(int idTrabajo, double montoFactura) {
        // Implementar la generación de la factura
    }
}
