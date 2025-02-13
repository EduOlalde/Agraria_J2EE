/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofield.modelos;

/**
 * Representa un rol en el sistema.
 *
 * @author Eduardo Olalde
 */
public class Rol {

    private final int idRol;
    private final Rol nombre;

    public Rol(int idRol, Rol nombre) {
        this.idRol = idRol;
        this.nombre = nombre;
    }

    public int getIdRol() {
        return idRol;
    }

    public Rol getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "Rol{" + "idRol=" + idRol + ", nombre=" + nombre + '}';
    }

}
