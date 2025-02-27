/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 *
 * @author Eduardo Olalde
 */
public class TipoTrabajoDAO {

    private final Connection conn;

    public TipoTrabajoDAO(Connection conn) {
        this.conn = conn;
    }

    // Obtener todos los tipos de trabajo
    public List<TipoTrabajo> obtenerTiposTrabajo() {
        List<TipoTrabajo> tiposTrabajo = new ArrayList<>();
        String sql = "SELECT * FROM tipo_trabajo";

        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
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
