package com.ecofield.dao;

import com.ecofield.modelos.TipoTrabajo;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta clase se encarga de interactuar con la base de datos para obtener información
 * sobre los tipos de trabajo disponibles en el sistema.
 */
public class TipoTrabajoDAO {

    private final Connection conn;

    /**
     * Constructor de la clase TipoTrabajoDAO.
     * 
     * @param conn Conexión a la base de datos.
     */
    public TipoTrabajoDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Obtiene todos los tipos de trabajo disponibles en la base de datos.
     * 
     * @return Lista de objetos TipoTrabajo que representan los tipos de trabajo
     *         disponibles en el sistema.
     *         Si no hay tipos de trabajo, se devuelve una lista vacía.
     */
    public List<TipoTrabajo> obtenerTiposTrabajo() {
        List<TipoTrabajo> tiposTrabajo = new ArrayList<>();
        String sql = "SELECT * FROM tipo_trabajo";

        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            // Iterar sobre los resultados y añadirlos a la lista
            while (rs.next()) {
                TipoTrabajo tipoTrabajo = new TipoTrabajo(
                        rs.getInt("ID_Tipo_Trabajo"),
                        rs.getString("Nombre"));
                tiposTrabajo.add(tipoTrabajo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MaquinistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tiposTrabajo;
    }
}
