/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofield.enums;

/**
 * Representa los diferentes tipos de trabajo agrícola disponibles.
 *
 * @author Eduardo Olalde
 */
public enum TipoTrabajo {
    ARAR("Arar"),
    COSECHAR("Cosechar"),
    SEMBRAR("Sembrar"),
    FUMIGAR("Fumigar"),
    FERTILIZAR("Fertilizar"),
    PODAR("Podar"),
    EMPACAR("Empacar");

    private final String descripcion;

    TipoTrabajo(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static TipoTrabajo fromString(String texto) {
        for (TipoTrabajo tipo : TipoTrabajo.values()) {
            if (tipo.descripcion.equalsIgnoreCase(texto)) {
                return tipo;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
