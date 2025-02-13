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

/**
 *
 * @author Eduardo Olalde
 */
public class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost/agricultura";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    public static Connection conectar(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
