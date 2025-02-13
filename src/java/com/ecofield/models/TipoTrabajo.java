/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofield.models;

/**
 * Representa un tipo de trabajo en el sistema.
 *
 * @author Eduardo Olalde
 */
public class TipoTrabajo {

    private final int idTipoTrabajo;
    private final TipoTrabajo nombre;

    public TipoTrabajo(int idTipoTrabajo, TipoTrabajo nombre) {
        this.idTipoTrabajo = idTipoTrabajo;
        this.nombre = nombre;
    }

    public int getIdTipoTrabajo() {
        return idTipoTrabajo;
    }

    public TipoTrabajo getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "TipoTrabajo{" + "idTipoTrabajo=" + idTipoTrabajo + ", nombre=" + nombre + '}';
    }

}
