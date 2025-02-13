/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofield.enums;

/**
 * Representa los roles de usuario en el sistema.
 *
 * @author Eduardo Olalde
 */
public enum RolUsuario {
    ADMINISTRADOR("Administrador"),
    AGRICULTOR("Agricultor"),
    MAQUINISTA("Maquinista");

    private final String descripcion;

    RolUsuario(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static RolUsuario fromString(String texto) {
        for (RolUsuario rol : RolUsuario.values()) {
            if (rol.descripcion.equalsIgnoreCase(texto)) {
                return rol;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
