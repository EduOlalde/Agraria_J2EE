package com.ecofield.modelos;

/**
 * Representa una máquina en el sistema
 *
 * @author Eduardo Olalde
 */
public class Maquina {

    private int idMaquina;
    private String estado;
    private int tipoMaquina;
    private String modelo;

    public Maquina(String estado, int tipoMaquina, String modelo) {
        this.estado = estado;
        this.tipoMaquina = tipoMaquina;
        this.modelo = modelo;
    }

    public Maquina(int idMaquina, String estado, int tipoMaquina, String modelo) {
        this.idMaquina = idMaquina;
        this.estado = estado;
        this.tipoMaquina = tipoMaquina;
        this.modelo = modelo;
    }

    public void setIdMaquina(int idMaquina) {
        this.idMaquina = idMaquina;
    }

    public int getIdMaquina() {
        return idMaquina;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
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
