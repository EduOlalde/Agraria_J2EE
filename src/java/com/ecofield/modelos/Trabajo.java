package com.ecofield.modelos;

import java.util.Date;

/**
 * Representa un trabajo en el sistema.
 *
 * @author Eduardo Olalde
 */
public class Trabajo {

    private int id;
    private int numParcela;
    private int idMaquina;
    private int idMaquinista;
    private Date fecInicio;
    private Date fecFin;
    private int horas;
    private int tipoTrabajo;
    private String estado;

    public Trabajo() {
    }

    public Trabajo(int id, int numParcela, int idMaquina, int idMaquinista, Date fecInicio, Date fecFin, int horas, int tipoTrabajo, String estado) {
        this.id = id;
        this.numParcela = numParcela;
        this.idMaquina = idMaquina;
        this.idMaquinista = idMaquinista;
        this.fecInicio = fecInicio;
        this.fecFin = fecFin;
        this.horas = horas;
        this.tipoTrabajo = tipoTrabajo;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumParcela() {
        return numParcela;
    }

    public void setNumParcela(int numParcela) {
        this.numParcela = numParcela;
    }

    public int getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(int idMaquina) {
        this.idMaquina = idMaquina;
    }

    public int getIdMaquinista() {
        return idMaquinista;
    }

    public void setIdMaquinista(int idMaquinista) {
        this.idMaquinista = idMaquinista;
    }

    public Date getFecInicio() {
        return fecInicio;
    }

    public void setFecInicio(Date fecInicio) {
        this.fecInicio = fecInicio;
    }

    public Date getFecFin() {
        return fecFin;
    }

    public void setFecFin(Date fecFin) {
        this.fecFin = fecFin;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public int getTipoTrabajo() {
        return tipoTrabajo;
    }

    public void setTipoTrabajo(int tipoTrabajo) {
        this.tipoTrabajo = tipoTrabajo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Trabajo{" + "id=" + id + ", numParcela=" + numParcela + ", idMaquina=" + idMaquina + ", idMaquinista=" + idMaquinista + ", fecInicio=" + fecInicio + ", fecFin=" + fecFin + ", horas=" + horas + ", tipoTrabajo=" + tipoTrabajo + ", estado=" + estado + '}';
    }

}
