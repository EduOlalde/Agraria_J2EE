package com.ecofield.modelos;

/**
 * Representa una máquina en el sistema.
 * Esta clase contiene la información sobre una máquina, como su ID, estado, tipo y modelo.
 * 
 * @author Eduardo Olalde
 */
public class Maquina {

    /** ID único de la máquina */
    private int idMaquina;

    /** Estado actual de la máquina (e.g., activa, inactiva, en reparación) */
    private String estado;

    /** Tipo de la máquina (e.g., tractor, cosechadora, etc.) */
    private int tipoMaquina;

    /** Modelo de la máquina */
    private String modelo;

    /**
     * Constructor de la clase Maquina.
     * 
     * @param estado El estado de la máquina (e.g., activa, inactiva).
     * @param tipoMaquina El tipo de la máquina (e.g., tractor, cosechadora).
     * @param modelo El modelo de la máquina.
     */
    public Maquina(String estado, int tipoMaquina, String modelo) {
        this.estado = estado;
        this.tipoMaquina = tipoMaquina;
        this.modelo = modelo;
    }

    /**
     * Constructor de la clase Maquina con ID.
     * 
     * @param idMaquina El ID único de la máquina.
     * @param estado El estado de la máquina.
     * @param tipoMaquina El tipo de la máquina.
     * @param modelo El modelo de la máquina.
     */
    public Maquina(int idMaquina, String estado, int tipoMaquina, String modelo) {
        this.idMaquina = idMaquina;
        this.estado = estado;
        this.tipoMaquina = tipoMaquina;
        this.modelo = modelo;
    }

    /**
     * Establece el ID de la máquina.
     * 
     * @param idMaquina El nuevo ID de la máquina.
     */
    public void setIdMaquina(int idMaquina) {
        this.idMaquina = idMaquina;
    }

    /**
     * Obtiene el ID de la máquina.
     * 
     * @return El ID de la máquina.
     */
    public int getIdMaquina() {
        return idMaquina;
    }

    /**
     * Obtiene el estado de la máquina.
     * 
     * @return El estado de la máquina.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado de la máquina.
     * 
     * @param estado El nuevo estado de la máquina.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el tipo de la máquina.
     * 
     * @return El tipo de la máquina.
     */
    public int getTipoMaquina() {
        return tipoMaquina;
    }

    /**
     * Establece el tipo de la máquina.
     * 
     * @param tipoMaquina El nuevo tipo de la máquina.
     */
    public void setTipoMaquina(int tipoMaquina) {
        this.tipoMaquina = tipoMaquina;
    }

    /**
     * Obtiene el modelo de la máquina.
     * 
     * @return El modelo de la máquina.
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Establece el modelo de la máquina.
     * 
     * @param modelo El nuevo modelo de la máquina.
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Devuelve una representación en formato de cadena de la máquina.
     * 
     * @return Una cadena que representa la máquina con todos sus atributos.
     */
    @Override
    public String toString() {
        return "Maquina{" + "idMaquina=" + idMaquina + ", estado=" + estado + ", tipoMaquina=" + tipoMaquina + ", modelo=" + modelo + '}';
    }
}
