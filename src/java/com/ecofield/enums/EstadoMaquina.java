/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofield.enums;

/**
 * Representa los posibles estados de una máquina agrícola.
 *
 * @author Eduardo Olalde
 */
public enum EstadoMaquina {
    DISPONIBLE("Disponible"),
    ASIGNADA("Asignada"),
    FUERA_DE_SERVICIO("Fuera de servicio");

    private final String descripcion;

    EstadoMaquina(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static EstadoMaquina fromString(String texto) {
        for (EstadoMaquina estado : EstadoMaquina.values()) {
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
