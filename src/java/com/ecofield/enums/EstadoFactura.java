/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofield.enums;

/**
 * Representa los posibles estados de una factura en el sistema.
 *
 * @author Eduardo Olalde
 */
public enum EstadoFactura {

    PENDIENTE_DE_GENERAR(
    "Pendiente de generar"),
    PENDIENTE_DE_PAGAR("Pendiente de pagar"),
    PAGADA("Pagada");

    private final String descripcion;

    /**
     * Constructor del enum.
     *
     * @param descripcion Descripción legible del estado.
     */
    EstadoFactura(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la descripción del estado de la factura.
     *
     * @return Descripción del estado.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Convierte una cadena en un `EstadoFactura` correspondiente.
     *
     * @param texto Estado en formato de cadena.
     * @return El `EstadoFactura` correspondiente, o `null` si no coincide.
     */
    public static EstadoFactura fromString(String texto) {
        for (EstadoFactura estado : EstadoFactura.values()) {
            if (estado.descripcion.equalsIgnoreCase(texto)) {
                return estado;
            }
        }
        return null; // O lanzar una excepción personalizada
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
