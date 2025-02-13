/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofield.enums;

/**
 * Representa los estados en los que puede estar un trabajo agrícola.
 *
 * @author Eduardo Olalde
 */
public enum EstadoTrabajo {
    PENDIENTE("Pendiente"),
    EN_CURSO("En curso"),
    FINALIZADO("Finalizado");

    private final String descripcion;

    EstadoTrabajo(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static EstadoTrabajo fromString(String texto) {
        for (EstadoTrabajo estado : EstadoTrabajo.values()) {
            if (estado.descripcion.equalsIgnoreCase(texto)) {
                return estado;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
