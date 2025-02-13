/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofield.enums;

/**
 * Representa los posibles estados de una solicitud de trabajo agrícola.
 *
 * @author Eduardo Olalde
 */
public enum EstadoTrabajoSolicitado {
    EN_REVISION("En revisión"),
    APROBADO("Aprobado"),
    RECHAZADO("Rechazado");

    private final String descripcion;

    EstadoTrabajoSolicitado(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static EstadoTrabajoSolicitado fromString(String texto) {
        for (EstadoTrabajoSolicitado estado : EstadoTrabajoSolicitado.values()) {
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
