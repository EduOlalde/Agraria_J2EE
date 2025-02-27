package com.ecofield.dao;

import com.ecofield.modelos.Parcela;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParcelaDAO {

    private final Connection conn;

    public ParcelaDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Método para el agricultor:
     * Comprueba que el usuario (por su id) tiene el rol "Agricultor" y,
     * en ese caso, devuelve todas las parcelas asociadas a dicho usuario.
     *
     * @param idUsuario ID del usuario en sesión.
     * @return Lista de parcelas del agricultor; lista vacía si no posee dicho rol.
     * @throws SQLException
     */
    public List<Parcela> obtenerParcelasDeAgricultor(int idUsuario) throws SQLException {
        // Verificar que el usuario tenga rol "Agricultor"
        String checkSql = "SELECT r.Nombre " +
                          "FROM usuarios_roles ur " +
                          "JOIN roles r ON ur.ID_Rol = r.ID_Rol " +
                          "WHERE ur.ID_Usuario = ?";
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
        }

        // Si es agricultor, se obtienen sus parcelas
        List<Parcela> parcelas = new ArrayList<>();
        String sql = "SELECT p.Num_Parcela, p.ID_Catastro, p.Extension, p.Propietario " +
                     "FROM parcelas p " +
                     "WHERE p.Propietario = ?";
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
        }
        return parcelas;
    }

    /**
     * Método para el administrador: obtiene las parcelas con filtros opcionales.
     *
     * @param filtroAgricultor Si se especifica, filtra por el ID del agricultor.
     * @param filtroExtension Si se especifica, filtra las parcelas con extensión menor a este valor.
     * @return Lista de parcelas que cumplen los filtros.
     * @throws SQLException
     */
    public List<Parcela> obtenerParcelas(Integer filtroAgricultor, Double filtroExtension) throws SQLException {
        List<Parcela> parcelas = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT p.Num_Parcela, p.ID_Catastro, p.Extension, p.Propietario " +
                                               "FROM parcelas p WHERE 1=1");
        List<Object> parametros = new ArrayList<>();
        if (filtroAgricultor != null) {
            sql.append(" AND p.Propietario = ?");
            parametros.add(filtroAgricultor);
        }
        if (filtroExtension != null) {
            sql.append(" AND p.Extension < ?");
            parametros.add(filtroExtension);
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int index = 1;
            for (Object param : parametros) {
                if (param instanceof Integer) {
                    stmt.setInt(index++, (Integer) param);
                } else if (param instanceof Double) {
                    stmt.setDouble(index++, (Double) param);
                }
            }
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
        }
        return parcelas;
    }

    /**
     * Método para el administrador: obtiene una parcela a partir de su ID de catastro.
     *
     * @param idCatastro ID de catastro de la parcela.
     * @return La parcela encontrada o null si no existe.
     * @throws SQLException
     */
    public Parcela obtenerParcelaPorId(String idCatastro) throws SQLException {
        Parcela parcela = null;
        String sql = "SELECT p.Num_Parcela, p.ID_Catastro, p.Extension, p.Propietario " +
                     "FROM parcelas p " +
                     "WHERE p.ID_Catastro = ?";
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
        }
        return parcela;
    }

    /**
     * Método para el administrador: crea una nueva parcela.
     *
     * @param parcela Objeto Parcela con los datos a insertar.
     * @return true si se inserta correctamente; false en caso contrario.
     * @throws SQLException
     */
    public boolean crearParcela(Parcela parcela) throws SQLException {
        String sql = "INSERT INTO parcelas (ID_Catastro, Extension, Propietario) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, parcela.getIdCatastro());
            stmt.setDouble(2, parcela.getExtension());
            stmt.setInt(3, parcela.getPropietario());
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Método para el administrador: actualiza los datos de una parcela.
     *
     * @param parcela Objeto Parcela con los datos actualizados.
     * @return true si se actualiza correctamente; false en caso contrario.
     * @throws SQLException
     */
    public boolean actualizarParcela(Parcela parcela) throws SQLException {
        String sql = "UPDATE parcelas SET Extension = ?, Propietario = ? WHERE ID_Catastro = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, parcela.getExtension());
            stmt.setInt(2, parcela.getPropietario());
            stmt.setString(3, parcela.getIdCatastro());
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Método para el administrador: elimina una parcela a partir de su ID de catastro.
     *
     * @param idCatastro ID de catastro de la parcela a eliminar.
     * @return true si se elimina correctamente; false en caso contrario.
     * @throws SQLException
     */
    public boolean eliminarParcela(String idCatastro) throws SQLException {
        String sql = "DELETE FROM parcelas WHERE ID_Catastro = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idCatastro);
            return stmt.executeUpdate() > 0;
        }
    }
}
