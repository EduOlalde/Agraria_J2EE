package com.ecofield.dao;

import com.ecofield.modelos.Parcela;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParcelaDAO {

    private final Connection conn;

    public ParcelaDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Método para el agricultor: Comprueba que el usuario (por su id) tiene el
     * rol "Agricultor" y, en ese caso, devuelve todas las parcelas asociadas a
     * dicho usuario.
     *
     * @param idUsuario ID del usuario en sesión.
     * @return Lista de parcelas del agricultor; lista vacía si no posee dicho
     * rol.
     */
    public List<Parcela> obtenerParcelasDeAgricultor(int idUsuario) {
        // Verificar que el usuario tenga rol "Agricultor"
        String checkSql = "SELECT r.Nombre "
                + "FROM usuarios_roles ur "
                + "JOIN roles r ON ur.ID_Rol = r.ID_Rol "
                + "WHERE ur.ID_Usuario = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setInt(1, idUsuario);
            try (ResultSet rsCheck = checkStmt.executeQuery()) {
                boolean esAgricultor = false;
                while (rsCheck.next()) {
                    String rol = rsCheck.getString("Nombre");
                    if ("Agricultor".equalsIgnoreCase(rol)) {
                        esAgricultor = true;
                        break;
                    }
                }
                if (!esAgricultor) {
                    // El usuario no es agricultor; se devuelve lista vacía (o lanzar excepción según necesidad)
                    return new ArrayList<>();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParcelaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Si es agricultor, se obtienen sus parcelas
        List<Parcela> parcelas = new ArrayList<>();
        String sql = "SELECT p.Num_Parcela, p.ID_Catastro, p.Extension, p.Propietario "
                + "FROM parcelas p "
                + "WHERE p.Propietario = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int numParcela = rs.getInt("Num_Parcela");
                    String idCatastro = rs.getString("ID_Catastro");
                    double extension = rs.getDouble("Extension");
                    int propietario = rs.getInt("Propietario");
                    Parcela parcela = new Parcela(numParcela, idCatastro, extension, propietario);
                    parcelas.add(parcela);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParcelaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parcelas;
    }

    /**
     * Método para el administrador: obtiene las parcelas con filtros
     * opcionales.
     *
     * @param agricultorFiltro Si se especifica, filtra por el ID del
     * agricultor.
     * @param extensionFiltro Si se especifica, filtra las parcelas con
     * extensión menor a este valor.
     * @return Lista de parcelas que cumplen los filtros.
     */
    public List<Parcela> obtenerParcelas(Integer agricultorFiltro, Double extensionFiltro) {
        List<Parcela> parcelas = new ArrayList<>();
        String sql = "SELECT p.Num_Parcela, p.ID_Catastro, p.Extension, p.Propietario FROM Parcelas p "
                + "JOIN usuarios u ON p.Propietario = u.ID_Usuario "
                + "WHERE 1";

        if (agricultorFiltro != null) {
            sql += " AND p.Propietario = ?";
        }
        if (extensionFiltro != null) {
            sql += " AND p.Extension < ?";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            int index = 1;

            if (agricultorFiltro != null) {
                stmt.setInt(index++, agricultorFiltro);
            }
            if (extensionFiltro != null) {
                stmt.setDouble(index++, extensionFiltro);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Parcela parcela = new Parcela(
                            rs.getInt("Num_Parcela"),
                            rs.getString("ID_Catastro"),
                            rs.getDouble("Extension"),
                            rs.getInt("Propietario")
                    );
                    parcelas.add(parcela);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ParcelaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parcelas;
    }

    /**
     * Método para el administrador: obtiene una parcela a partir de su ID de
     * catastro.
     *
     * @param idCatastro ID de catastro de la parcela.
     * @return La parcela encontrada o null si no existe.
     */
    public Parcela obtenerParcelaPorId(String idCatastro) {
        Parcela parcela = null;
        String sql = "SELECT p.Num_Parcela, p.ID_Catastro, p.Extension, p.Propietario "
                + "FROM parcelas p "
                + "WHERE p.ID_Catastro = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idCatastro);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int numParcela = rs.getInt("Num_Parcela");
                    String idCat = rs.getString("ID_Catastro");
                    double extension = rs.getDouble("Extension");
                    int propietario = rs.getInt("Propietario");
                    parcela = new Parcela(numParcela, idCat, extension, propietario);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParcelaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parcela;
    }

    /**
     * Método para el administrador: crea una nueva parcela.
     *
     * @param parcela Objeto Parcela con los datos a insertar.
     * @return true si se inserta correctamente; false en caso contrario.
     */
    public boolean crearParcela(Parcela parcela) {
        String sql = "INSERT INTO parcelas (ID_Catastro, Extension, Propietario) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, parcela.getIdCatastro());
            stmt.setDouble(2, parcela.getExtension());
            stmt.setInt(3, parcela.getPropietario());
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ParcelaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Método para el administrador: actualiza los datos de una parcela.
     *
     * @param parcela Objeto Parcela con los datos actualizados.
     * @return true si se actualiza correctamente; false en caso contrario.
     */
    public boolean actualizarParcela(Parcela parcela) {
        String sql = "UPDATE parcelas SET Extension = ?, Propietario = ? WHERE ID_Catastro = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, parcela.getExtension());
            stmt.setInt(2, parcela.getPropietario());
            stmt.setString(3, parcela.getIdCatastro());
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ParcelaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Método para el administrador: elimina una parcela a partir de su ID de
     * catastro.
     *
     * @param idCatastro ID de catastro de la parcela a eliminar.
     * @return true si se elimina correctamente; false en caso contrario.
     */
    public boolean eliminarParcela(String idCatastro) {
        String sql = "DELETE FROM parcelas WHERE ID_Catastro = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idCatastro);
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ParcelaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Obtiene el propietario de una parcela dado su número de parcela.
     *
     * @param numParcela Número de la parcela.
     * @return ID del propietario o -1 si no se encuentra.
     */
    public int obtenerPropietarioPorNumParcela(int numParcela) {
        String sql = "SELECT Propietario FROM parcelas WHERE Num_Parcela = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, numParcela);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("Propietario");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParcelaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1; // Retorna -1 si no encuentra un propietario.
    }
}
