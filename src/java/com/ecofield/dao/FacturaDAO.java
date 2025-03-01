/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 *
 * @author Eduardo Olalde
 */
public class FacturaDAO {

    private final Connection conn;

    public FacturaDAO(Connection conn) {
        this.conn = conn;
    }

    // Obtener facturas pendientes de generar
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

    // Obtener facturas pendientes de pago con opción de filtrar por agricultor
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

    // Obtener facturas pagadas con opción de filtrar por agricultor
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

    // Actualizar el estado de la factura y asignar fechas según corresponda
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

    // Método para eliminar factura (en caso de error u otro evento)
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
