package com.ecofield.dao;

import com.ecofield.modelos.Rol;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta clase se encarga de interactuar con la base de datos para obtener información sobre los roles.
 * Permite recuperar todos los roles disponibles en el sistema.
 */
public class RolDAO {
    private final Connection conn;

    /**
     * Constructor de la clase RolDAO.
     * 
     * @param conn Conexión a la base de datos.
     */
    public RolDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Obtiene todos los roles disponibles en la base de datos.
     * 
     * @return Lista de objetos Rol que representan los roles disponibles en el sistema.
     *         Si no hay roles, se devuelve una lista vacía.
     */
    public List<Rol> obtenerRolesDisponibles() {
        List<Rol> roles = new ArrayList<>();
        String sql = "SELECT * FROM roles";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // Crear un objeto Rol a partir de los datos obtenidos
                Rol rol = new Rol(
                        rs.getInt("ID_Rol"),
                        rs.getString("Nombre")
                );
                roles.add(rol);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RolDAO.class.getName()).log(Level.SEVERE, null, ex);
        }      
        return roles;
    }
}
