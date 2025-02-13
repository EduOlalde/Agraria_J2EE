/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofield.modelos;

/**
 * Representa una especialidad de maquinista en el sistema.
 *
 * @author Eduardo Olalde
 */
public class MaquinistaTipoTrabajo {

    private int idMaquinista;
    private int idTipoTrabajo;

    public MaquinistaTipoTrabajo(int idMaquinista, int idTipoTrabajo) {
        this.idMaquinista = idMaquinista;
        this.idTipoTrabajo = idTipoTrabajo;
    }

    public int getIdMaquinista() {
        return idMaquinista;
    }

    public void setIdMaquinista(int idMaquinista) {
        this.idMaquinista = idMaquinista;
    }

    public int getIdTipoTrabajo() {
        return idTipoTrabajo;
    }

    public void setIdTipoTrabajo(int idTipoTrabajo) {
        this.idTipoTrabajo = idTipoTrabajo;
    }

    @Override
    public String toString() {
        return "MaquinistaTipoTrabajo{" + "idMaquinista=" + idMaquinista + ", idTipoTrabajo=" + idTipoTrabajo + '}';
    }

}
