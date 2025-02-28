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

    // Obtener facturas pendientes de pago
    public List<Factura> obtenerFacturasPendientesPago() {
        List<Factura> facturas = new ArrayList<>();
        String sql = "SELECT f.ID_Factura, f.ID_Trabajo, f.Estado, f.Fecha_Emision, f.Fecha_Pago, f.Monto "
                + "FROM facturas f WHERE f.Estado = 'Pendiente de pagar'";

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

    // Crear una nueva factura
    public boolean generarFactura(int idFactura) {
        String sql = "UPDATE facturas SET Estado = 'Pendiente de pagar', Fecha_Emision = CURDATE() WHERE ID_Factura = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idFactura);
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(FacturaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    // Actualizar el estado de la factura
    public boolean actualizarEstadoFactura(int idFactura, String nuevoEstado, Date fechaPago, Double monto) {
        String sql = "UPDATE facturas SET Estado = ?, Fecha_Pago = ?, Monto = ? WHERE ID_Factura = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nuevoEstado);
            stmt.setDate(2, fechaPago);
            stmt.setDouble(3, monto);
            stmt.setInt(4, idFactura);
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(FacturaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Factura> obtenerHistorialFacturas() {
        List<Factura> facturas = new ArrayList<>();
        String sql = "SELECT f.ID_Factura, f.ID_Trabajo, f.Estado, f.Fecha_Emision, f.Fecha_Pago, f.Monto "
                + "FROM facturas f WHERE f.Estado IN ('Pendiente de pagar', 'Pagada')";

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

    // Obtener todas las facturas
    public List<Factura> obtenerTodasLasFacturas() {
        List<Factura> facturas = new ArrayList<>();
        String sql = "SELECT f.ID_Factura, f.ID_Trabajo, f.Estado, f.Fecha_Emision, f.Fecha_Pago, f.Monto "
                + "FROM facturas f";

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

    // Obtener facturas de un trabajo específico
    public List<Factura> obtenerFacturasPorTrabajo(int idTrabajo) {
        List<Factura> facturas = new ArrayList<>();
        String sql = "SELECT f.ID_Factura, f.Estado, f.Fecha_Emision, f.Fecha_Pago, f.Monto "
                + "FROM facturas f WHERE f.ID_Trabajo = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idTrabajo);
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

    // Método para registrar una factura pagada
    public boolean registrarPagoFactura(int idFactura, Date fechaPago, double monto) throws SQLException {
        return actualizarEstadoFactura(idFactura, "Pagada", fechaPago, monto);
    }

    // Método para eliminar factura (en caso de error u otro evento)
    public boolean eliminarFactura(int idFactura) throws SQLException {
        String sql = "DELETE FROM facturas WHERE ID_Factura = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idFactura);
            return stmt.executeUpdate() > 0;
        }
    }

}
