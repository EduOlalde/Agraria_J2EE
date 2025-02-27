/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 *
 * @author Eduardo Olalde
 */
public class RolDAO {
    private final Connection conn;

    public RolDAO(Connection conn) {
        this.conn = conn;
    }
    
    public List<Rol> obtenerRolesDisponibles(){
        List<Rol> roles = new ArrayList<>();
        String sql = "SELECT * FROM roles";
        
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
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
