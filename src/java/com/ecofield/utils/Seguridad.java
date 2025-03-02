/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofield.utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Eduardo Olalde
 */
public class Seguridad {

    /**
     * Convierte el salt de PHP ($2y$) a un formato compatible con BCrypt de
     * Java ($2a$).
     *
     * @param saltPHP PHP salt con el prefijo $2y$.
     * @return Salt con el formato adecuado para BCrypt de Java ($2a$).
     */
    public static String convertirSaltPHPaJava(String saltPHP) {
        if (saltPHP.startsWith("$2y$")) {
            return "$2a$" + saltPHP.substring(4); // Reemplazar $2y$ con $2a$
        }
        return saltPHP;
    }

    /**
     * Genera un hash seguro de la contraseña utilizando BCrypt.
     *
     * @param contrasena La contraseña en texto plano.
     * @return Un hash seguro de la contraseña.
     */
    public static String hashPassword(String contrasena) {
        return BCrypt.hashpw(contrasena, BCrypt.gensalt(12)); // Factor de costo 12
    }

    /**
     * Verifica si una contraseña coincide con su hash almacenado, considerando
     * el salt PHP convertido.
     *
     * @param contrasena La contraseña en texto plano.
     * @param hash El hash almacenado de la contraseña.
     * @return {@code true} si la contraseña coincide, {@code false} en caso
     * contrario.
     */
    public static boolean verificarPassword(String contrasena, String hash) {
        // Extraer el salt del hash almacenado
        String salt = hash.substring(0, 29); // El salt tiene 29 caracteres
        String saltCompatible = convertirSaltPHPaJava(salt); // Convertir el salt PHP a formato compatible con Java

        // Verificar la contraseña usando el salt modificado y el hash almacenado
        return BCrypt.checkpw(contrasena, saltCompatible + hash.substring(29)); // Restante es el hash real
    }
}
