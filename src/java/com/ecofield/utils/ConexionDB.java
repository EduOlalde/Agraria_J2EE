/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofield.utils;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost/agriculturapruebas";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    // Método para obtener la conexión
    public static Connection conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, "Error al conectar a la base de datos", ex);
            return null;
        }
    }

    // Método para cerrar la conexión
    public static void cerrarConexion(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                Logger.getLogger(ConexionDB.class.getName()).log(Level.INFO, "Conexión cerrada correctamente");
            } catch (SQLException ex) {
                Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, "Error al cerrar la conexión", ex);
            }
        }
    }
}

