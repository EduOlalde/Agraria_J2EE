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
 * La clase {@code ConexionDB} maneja las operaciones de conexión y cierre de la base de datos.
 * Proporciona métodos estáticos para establecer y cerrar una conexión con una base de datos MySQL.
 * 
 * @author Eduardo Olalde
 */
public class ConexionDB {
    
    /** La URL de la base de datos */
    private static final String URL = "jdbc:mysql://localhost/agriculturapruebas";
    
    /** El nombre de usuario para la base de datos */
    private static final String USER = "root";
    
    /** La contraseña para la base de datos */
    private static final String PASSWORD = "";

    /**
     * Establece una conexión con la base de datos MySQL.
     * 
     * Este método utiliza la URL, el usuario y la contraseña predefinidos para conectar con la base de datos.
     * Si la conexión es exitosa, se devuelve un objeto {@link Connection}, de lo contrario, 
     * se maneja el error y se retorna {@code null}.
     * 
     * @return La conexión a la base de datos si es exitosa, {@code null} en caso de error.
     */
    public static Connection conectar() {
        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.jdbc.Driver");
            return (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException ex) {
            // Manejar excepciones de conexión
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, "Error al conectar a la base de datos", ex);
            return null;
        }
    }

    /**
     * Cierra una conexión a la base de datos.
     * 
     * Este método cierra la conexión a la base de datos si no es {@code null}.
     * Si ocurre un error durante el cierre, se maneja mediante un registro de error.
     * 
     * @param conn La conexión a cerrar.
     */
    public static void cerrarConexion(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                // Log para indicar que la conexión se cerró correctamente
                Logger.getLogger(ConexionDB.class.getName()).log(Level.INFO, "Conexión cerrada correctamente");
            } catch (SQLException ex) {
                // Manejar excepciones al cerrar la conexión
                Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, "Error al cerrar la conexión", ex);
            }
        }
    }
}
