package com.ecofield.dao;

import com.ecofield.modelos.Factura;
import com.mysql.jdbc.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase DAO (Data Access Object) que gestiona las operaciones relacionadas con la tabla de facturas en la base de datos.
 * Proporciona métodos para obtener, actualizar y eliminar facturas, así como para obtener facturas según su estado.
 * 
 * @author Eduardo Olalde
 */
public class FacturaDAO {

    private final Connection conn;

    /**
     * Constructor de la clase FacturaDAO.
     * 
     * @param conn La conexión a la base de datos.
     */
    public FacturaDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Obtiene las facturas que están pendientes de generar.
     * 
     * @return Una lista de objetos Factura con las facturas pendientes de generar.
     */
    public List<Factura> obtenerFacturasPendientes() {
        List<Factura> facturas = new ArrayList<>();
        String sql = "SELECT f.ID_Factura, f.ID_Trabajo, f.Estado, f.Fecha_Emision, f.Fecha_Pago, f.Monto "
                + "FROM facturas f WHERE f.Estado = 'Pendiente de generar'";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Factura factura = new Factura(
                        rs.getInt("ID_Factura"),
                        rs.getInt("ID_Trabajo"),
                        rs.getString("Estado"),
                        rs.getDate("Fecha_Emision"),
                        rs.getDate("Fecha_Pago"),
                        rs.getDouble("Monto")
                );
                facturas.add(factura);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return facturas;
    }

    /**
     * Obtiene las facturas pendientes de pago, con la opción de filtrar por agricultor.
     * 
     * @param idAgricultor El ID del agricultor para filtrar las facturas. Si es null, se obtienen todas las facturas pendientes de pago.
     * @return Una lista de objetos Factura con las facturas pendientes de pago.
     */
    public List<Factura> obtenerFacturasPendientesPago(Integer idAgricultor) {
        List<Factura> facturas = new ArrayList<>();
        String sql = "SELECT f.ID_Factura, f.ID_Trabajo, f.Estado, f.Fecha_Emision, f.Fecha_Pago, f.Monto "
                + "FROM facturas f "
                + "JOIN trabajos t ON f.ID_Trabajo = t.ID_Trabajo "
                + "JOIN parcelas p ON t.Num_Parcela = p.Num_Parcela "
                + (idAgricultor != null ? "WHERE p.Propietario = ? AND f.Estado = 'Pendiente de pagar'"
                        : "WHERE f.Estado = 'Pendiente de pagar'");

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (idAgricultor != null) {
                stmt.setInt(1, idAgricultor);
            }
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Factura factura = new Factura(
                        rs.getInt("ID_Factura"),
                        rs.getInt("ID_Trabajo"),
                        rs.getString("Estado"),
                        rs.getDate("Fecha_Emision"),
                        rs.getDate("Fecha_Pago"),
                        rs.getDouble("Monto")
                );
                facturas.add(factura);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return facturas;
    }

    /**
     * Obtiene las facturas que ya han sido pagadas, con la opción de filtrar por agricultor.
     * 
     * @param idAgricultor El ID del agricultor para filtrar las facturas. Si es null, se obtienen todas las facturas pagadas.
     * @return Una lista de objetos Factura con las facturas pagadas.
     */
    public List<Factura> obtenerFacturasPagadas(Integer idAgricultor) {
        List<Factura> facturas = new ArrayList<>();
        String sql = "SELECT f.ID_Factura, f.ID_Trabajo, f.Estado, f.Fecha_Emision, f.Fecha_Pago, f.Monto "
                + "FROM facturas f "
                + "JOIN trabajos t ON f.ID_Trabajo = t.ID_Trabajo "
                + "JOIN parcelas p ON t.Num_Parcela = p.Num_Parcela "
                + (idAgricultor != null ? "WHERE p.Propietario = ? AND f.Estado = 'Pagada'"
                        : "WHERE f.Estado = 'Pagada'");

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (idAgricultor != null) {
                stmt.setInt(1, idAgricultor);
            }
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Factura factura = new Factura(
                        rs.getInt("ID_Factura"),
                        rs.getInt("ID_Trabajo"),
                        rs.getString("Estado"),
                        rs.getDate("Fecha_Emision"),
                        rs.getDate("Fecha_Pago"),
                        rs.getDouble("Monto")
                );
                facturas.add(factura);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return facturas;
    }

    /**
     * Actualiza el estado de una factura y asigna las fechas correspondientes, según el nuevo estado.
     * 
     * @param idFactura El ID de la factura a actualizar.
     * @param nuevoEstado El nuevo estado de la factura (Pendiente de pagar, Pagada, etc.).
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean actualizarEstadoFactura(int idFactura, String nuevoEstado) {
        String sql;
        Date fechaActual = new Date(System.currentTimeMillis());

        if (null == nuevoEstado) {
            sql = "UPDATE facturas SET Estado = ? WHERE ID_Factura = ?";
        } else {
            switch (nuevoEstado) {
                case "Pendiente de pagar":
                    sql = "UPDATE facturas SET Estado = ?, Fecha_Emision = ? WHERE ID_Factura = ?";
                    break;
                case "Pagada":
                    sql = "UPDATE facturas SET Estado = ?, Fecha_Pago = ? WHERE ID_Factura = ?";
                    break;
                default:
                    sql = "UPDATE facturas SET Estado = ? WHERE ID_Factura = ?";
                    break;
            }
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nuevoEstado);
            if ("Pendiente de pagar".equals(nuevoEstado) || "Pagada".equals(nuevoEstado)) {
                stmt.setDate(2, fechaActual);
                stmt.setInt(3, idFactura);
            } else {
                stmt.setInt(2, idFactura);
            }
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(FacturaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Obtiene el historial de facturas, que incluye las facturas pendientes de pago y las pagadas, con la opción de filtrar por agricultor.
     * 
     * @param idAgricultor El ID del agricultor para filtrar las facturas. Si es null, se obtienen todas las facturas.
     * @return Una lista de objetos Factura con el historial de facturas.
     */
    public List<Factura> obtenerHistorialFacturas(Integer idAgricultor) {
        List<Factura> facturas = new ArrayList<>();
        String sql = "SELECT f.ID_Factura, f.ID_Trabajo, f.Estado, f.Fecha_Emision, f.Fecha_Pago, f.Monto "
                + "FROM facturas f "
                + "JOIN trabajos t ON f.ID_Trabajo = t.ID_Trabajo "
                + "JOIN parcelas p ON t.Num_Parcela = p.Num_Parcela "
                + (idAgricultor != null ? "WHERE p.Propietario = ? AND f.Estado IN ('Pendiente de pagar', 'Pagada')"
                        : "WHERE f.Estado IN ('Pendiente de pagar', 'Pagada')");

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (idAgricultor != null) {
                stmt.setInt(1, idAgricultor);
            }
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Factura factura = new Factura(
                        rs.getInt("ID_Factura"),
                        rs.getInt("ID_Trabajo"),
                        rs.getString("Estado"),
                        rs.getDate("Fecha_Emision"),
                        rs.getDate("Fecha_Pago"),
                        rs.getDouble("Monto")
                );
                facturas.add(factura);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return facturas;
    }

    /**
     * Elimina una factura de la base de datos.
     * 
     * @param idFactura El ID de la factura a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public boolean eliminarFactura(int idFactura) {
        String sql = "DELETE FROM facturas WHERE ID_Factura = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idFactura);
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(FacturaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
