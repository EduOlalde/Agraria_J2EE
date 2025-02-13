
package com.ecofield.models;

import com.ecofield.enums.EstadoMaquina;

/**
 * Representa una máquina en el sistema
 *
 * @author Eduardo Olalde
 */
public class Maquina {

    private final int idMaquina;
    private EstadoMaquina estado;
    private int tipoMaquina;
    private String modelo;

    public Maquina(int idMaquina, EstadoMaquina estado, int tipoMaquina, String modelo) {
        this.idMaquina = idMaquina;
        this.estado = estado;
        this.tipoMaquina = tipoMaquina;
        this.modelo = modelo;
    }

    public int getIdMaquina() {
        return idMaquina;
    }

    public EstadoMaquina getEstado() {
        return estado;
    }

    public void setEstado(EstadoMaquina estado) {
        this.estado = estado;
    }

    public int getTipoMaquina() {
        return tipoMaquina;
    }

    public void setTipoMaquina(int tipoMaquina) {
        this.tipoMaquina = tipoMaquina;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    @Override
    public String toString() {
        return "Maquina{" + "idMaquina=" + idMaquina + ", estado=" + estado + ", tipoMaquina=" + tipoMaquina + ", modelo=" + modelo + '}';
    }
}
