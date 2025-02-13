/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofield.modelos;

/**
 * Representa un rol de usuario en el sistema.
 *
 * @author Eduardo Olalde
 */
public class UsuarioRol {

    private final int idUsuario;
    private final int idRol;

    public UsuarioRol(int idUsuario, int idRol) {
        this.idUsuario = idUsuario;
        this.idRol = idRol;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public int getIdRol() {
        return idRol;
    }

    @Override
    public String toString() {
        return "UsuarioRol{" + "idUsuario=" + idUsuario + ", idRol=" + idRol + '}';
    }

}
