/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofield.modelos;

/**
 * Representa un tipo de trabajo en el sistema.
 *
 * @author Eduardo Olalde
 */
public class TipoTrabajo {

    private int idTipoTrabajo;
    private String nombre;

    public TipoTrabajo() {
    }

    public TipoTrabajo(int idTipoTrabajo, String nombre) {
        this.idTipoTrabajo = idTipoTrabajo;
        this.nombre = nombre;
    }

    public int getIdTipoTrabajo() {
        return idTipoTrabajo;
    }

    public void setIdTipoTrabajo(int idTipoTrabajo) {
        this.idTipoTrabajo = idTipoTrabajo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "TipoTrabajo{" + "idTipoTrabajo=" + idTipoTrabajo + ", nombre=" + nombre + '}';
    }

}
