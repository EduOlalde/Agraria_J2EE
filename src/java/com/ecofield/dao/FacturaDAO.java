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
 * Clase DAO (Data Access Object) que gestiona las operaciones relacionadas con
 * la tabla de facturas en la base de datos. Proporciona métodos para obtener,
 * actualizar y eliminar facturas, así como para obtener facturas según su
 * estado.
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
     * Actualiza el estado de una factura y asigna las fechas correspondientes,
     * según el nuevo estado.
     *
     * @param idFactura El ID de la factura a actualizar.
     * @param nuevoEstado El nuevo estado de la factura (Pendiente de pagar,
     * Pagada, etc.).
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

    /**
     * Obtiene las facturas según el estado especificado, con la opción de
     * filtrar por agricultor.
     *
     * @param idAgricultor El ID del agricultor para filtrar las facturas. Si es
     * null, se obtienen todas las facturas.
     * @param estado El estado de las facturas a obtener. Puede ser "Pendiente
     * de generar", "Pendiente de pagar", "Pagada" o "Historial" (que incluye
     * "Pendiente de pagar" y "Pagada").
     * @return Una lista de objetos Factura con las facturas filtradas por el
     * criterio dado.
     */
    public List<Factura> obtenerFacturas(Integer idAgricultor, String estado) {
        List<Factura> facturas = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT f.ID_Factura, f.ID_Trabajo, f.Estado, f.Fecha_Emision, f.Fecha_Pago, f.Monto "
                + "FROM facturas f ");

        boolean filtrarPorParcela = idAgricultor != null && !"Pendiente de generar".equals(estado);

        if (filtrarPorParcela) {
            sql.append("JOIN trabajos t ON f.ID_Trabajo = t.ID_Trabajo ")
                    .append("JOIN parcelas p ON t.Num_Parcela = p.Num_Parcela ");
        }

        sql.append("WHERE ");

        if ("Historial".equals(estado)) {
            sql.append("f.Estado IN ('Pendiente de pagar', 'Pagada')");
        } else {
            sql.append("f.Estado = ?");
        }

        if (filtrarPorParcela) {
            sql.append(" AND p.Propietario = ?");
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (!"Historial".equals(estado)) {
                stmt.setString(paramIndex++, estado);
            }

            if (filtrarPorParcela) {
                stmt.setInt(paramIndex, idAgricultor);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    facturas.add(new Factura(
                            rs.getInt("ID_Factura"),
                            rs.getInt("ID_Trabajo"),
                            rs.getString("Estado"),
                            rs.getDate("Fecha_Emision"),
                            rs.getDate("Fecha_Pago"),
                            rs.getDouble("Monto")
                    ));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return facturas;
    }

}
