package com.ecofield.dao;

import com.ecofield.modelos.TrabajoSolicitado;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrabajoSolicitadoDAO {

    private final Connection conn;

    /**
     * Constructor de la clase TrabajoSolicitadoDAO.
     * 
     * @param conn La conexión a la base de datos.
     */
    public TrabajoSolicitadoDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Crea una nueva solicitud de trabajo en la base de datos.
     * 
     * @param solicitud El objeto TrabajoSolicitado que contiene los datos de la solicitud.
     * @return true si la solicitud fue creada con éxito, false en caso contrario.
     */
    public boolean crearSolicitud(TrabajoSolicitado solicitud) {
        String sql = "INSERT INTO trabajos_solicitados (Num_Parcela, Propietario, ID_Tipo_Trabajo, Fecha_Solicitud) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, solicitud.getNumParcela());
            stmt.setInt(2, solicitud.getPropietario());
            stmt.setInt(3, solicitud.getIdTipoTrabajo());

            // Establecer la fecha actual
            java.sql.Date fechaActual = new java.sql.Date(System.currentTimeMillis());
            stmt.setDate(4, fechaActual);

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TrabajoSolicitadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Obtiene todos los trabajos solicitados de un agricultor por su ID de propietario.
     * 
     * @param idPropietario El ID del propietario de los trabajos solicitados.
     * @return Una lista de objetos TrabajoSolicitado.
     */
    public List<TrabajoSolicitado> obtenerTrabajosSolicitadosPorPropietario(int idPropietario) {
        String sql = "SELECT ID_Solicitud, Num_Parcela, Propietario, ID_Tipo_Trabajo, Estado, Fecha_Solicitud "
                + "FROM trabajos_solicitados WHERE Propietario = ?";
        List<TrabajoSolicitado> trabajosSolicitados = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPropietario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    trabajosSolicitados.add(new TrabajoSolicitado(
                            rs.getInt("ID_Solicitud"),
                            rs.getInt("Num_Parcela"),
                            rs.getInt("Propietario"),
                            rs.getInt("ID_Tipo_Trabajo"),
                            rs.getString("Estado"),
                            rs.getDate("Fecha_Solicitud")
                    ));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrabajoSolicitadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return trabajosSolicitados;
    }

    /**
     * Obtiene las solicitudes de trabajo filtradas únicamente por estado.
     * 
     * @param estado El estado de las solicitudes de trabajo.
     * @return Una lista de objetos TrabajoSolicitado.
     */
    public List<TrabajoSolicitado> obtenerTrabajosSolicitadosPorEstado(String estado) {
        List<TrabajoSolicitado> trabajosSolicitados = new ArrayList<>();
        String sql = "SELECT ID_Solicitud, Num_Parcela, Propietario, ID_Tipo_Trabajo, Estado, Fecha_Solicitud "
                + "FROM trabajos_solicitados WHERE Estado = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estado);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                trabajosSolicitados.add(new TrabajoSolicitado(
                        rs.getInt("ID_Solicitud"),
                        rs.getInt("Num_Parcela"),
                        rs.getInt("Propietario"),
                        rs.getInt("ID_Tipo_Trabajo"),
                        rs.getString("Estado"),
                        rs.getDate("Fecha_Solicitud")
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrabajoSolicitadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return trabajosSolicitados;
    }

    /**
     * Obtiene las solicitudes de trabajo filtradas por propietario y estado.
     * 
     * @param propietario El ID del propietario de los trabajos solicitados.
     * @param estado El estado de las solicitudes de trabajo.
     * @return Una lista de objetos TrabajoSolicitado.
     */
    public List<TrabajoSolicitado> obtenerTrabajosSolicitadosPorPropietarioYEstado(int propietario, String estado) {
        List<TrabajoSolicitado> trabajosSolicitados = new ArrayList<>();
        String sql = "SELECT ID_Solicitud, Num_Parcela, Propietario, ID_Tipo_Trabajo, Estado, Fecha_Solicitud "
                + "FROM trabajos_solicitados WHERE Propietario = ? AND Estado = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, propietario);
            stmt.setString(2, estado);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                trabajosSolicitados.add(new TrabajoSolicitado(
                        rs.getInt("ID_Solicitud"),
                        rs.getInt("Num_Parcela"),
                        rs.getInt("Propietario"),
                        rs.getInt("ID_Tipo_Trabajo"),
                        rs.getString("Estado"),
                        rs.getDate("Fecha_Solicitud")
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrabajoSolicitadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return trabajosSolicitados;
    }

    /**
     * Actualiza el estado de una solicitud de trabajo a "Rechazado".
     * 
     * @param idSolicitud El ID de la solicitud de trabajo a actualizar.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean rechazarTrabajo(int idSolicitud) {
        String sql = "UPDATE trabajos_solicitados SET Estado = 'Rechazado' WHERE ID_Solicitud = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idSolicitud);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TrabajoSolicitadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Asigna un trabajo, insertando un nuevo registro en la tabla de trabajos, 
     * actualizando la solicitud y marcando la máquina como asignada.
     * 
     * @param idSolicitud El ID de la solicitud de trabajo.
     * @param idMaquinista El ID del maquinista que se le asignará el trabajo.
     * @param idMaquina El ID de la máquina que se le asignará el trabajo.
     * @return true si la asignación fue exitosa, false en caso contrario.
     */
    public boolean asignarTrabajo(int idSolicitud, int idMaquinista, int idMaquina) {
        String sqlTrabajo = "INSERT INTO trabajos (Num_Parcela, Tipo, ID_Maquinista, ID_Maquina, Estado) VALUES (?, ?, ?, ?, 'Pendiente')";
        String sqlSolicitud = "UPDATE trabajos_solicitados SET Estado = 'Aprobado' WHERE ID_Solicitud = ?";
        String sqlMaquina = "UPDATE maquinas SET Estado = 'Asignada' WHERE ID_Maquina = ?";
        try (PreparedStatement stmtTrabajo = conn.prepareStatement(sqlTrabajo); PreparedStatement stmtSolicitud = conn.prepareStatement(sqlSolicitud); PreparedStatement stmtMaquina = conn.prepareStatement(sqlMaquina)) {

            // Obtener datos de la solicitud
            TrabajoSolicitado solicitud = obtenerTrabajoSolicitado(idSolicitud);
            if (solicitud == null) {
                return false;
            }
            stmtTrabajo.setInt(1, solicitud.getNumParcela());
            stmtTrabajo.setInt(2, solicitud.getIdTipoTrabajo());
            stmtTrabajo.setInt(3, idMaquinista);
            stmtTrabajo.setInt(4, idMaquina);
            stmtTrabajo.executeUpdate();

            stmtSolicitud.setInt(1, idSolicitud);
            stmtSolicitud.executeUpdate();

            stmtMaquina.setInt(1, idMaquina);
            stmtMaquina.executeUpdate();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(TrabajoSolicitadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Obtiene una solicitud de trabajo por su ID.
     * 
     * @param idSolicitud El ID de la solicitud de trabajo a obtener.
     * @return El objeto TrabajoSolicitado correspondiente, o null si no se encuentra.
     */
    public TrabajoSolicitado obtenerTrabajoSolicitado(int idSolicitud) {
        String sql = "SELECT ID_Solicitud, Num_Parcela, Propietario, ID_Tipo_Trabajo, Estado, Fecha_Solicitud "
                + "FROM trabajos_solicitados WHERE ID_Solicitud = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idSolicitud);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new TrabajoSolicitado(
                            rs.getInt("ID_Solicitud"),
                            rs.getInt("Num_Parcela"),
                            rs.getInt("Propietario"),
                            rs.getInt("ID_Tipo_Trabajo"),
                            rs.getString("Estado"),
                            rs.getDate("Fecha_Solicitud")
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrabajoSolicitadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
