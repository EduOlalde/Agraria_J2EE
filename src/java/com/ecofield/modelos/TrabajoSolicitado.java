package com.ecofield.modelos;

import java.util.Date;

/**
 * Representa un trabajo solicitado en el sistema.
 *
 * @author Eduardo Olalde
 */
public class TrabajoSolicitado {

    private int idSolicitud;
    private int numParcela;
    private int propietario;
    private int idTipoTrabajo;
    private String estado;
    private Date fechaSolicitud;

    public TrabajoSolicitado(int numParcela, int propietario, int idTipoTrabajo) {
        this.numParcela = numParcela;
        this.propietario = propietario;
        this.idTipoTrabajo = idTipoTrabajo;
    }

    public TrabajoSolicitado(int idSolicitud, int numParcela, int propietario, int idTipoTrabajo, String estado, Date fechaSolicitud) {
        this.idSolicitud = idSolicitud;
        this.numParcela = numParcela;
        this.propietario = propietario;
        this.idTipoTrabajo = idTipoTrabajo;
        this.estado = estado;
        this.fechaSolicitud = fechaSolicitud;
    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public int getNumParcela() {
        return numParcela;
    }

    public void setNumParcela(int numParcela) {
        this.numParcela = numParcela;
    }

    public int getPropietario() {
        return propietario;
    }

    public void setPropietario(int propietario) {
        this.propietario = propietario;
    }

    public int getIdTipoTrabajo() {
        return idTipoTrabajo;
    }

    public void setIdTipoTrabajo(int idTipoTrabajo) {
        this.idTipoTrabajo = idTipoTrabajo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    @Override
    public String toString() {
        return "TrabajoSolicitado{" + "idSolicitud=" + idSolicitud + ", numParcela=" + numParcela + ", propietario=" + propietario + ", idTipoTrabajo=" + idTipoTrabajo + ", estado=" + estado + ", fechaSolicitud=" + fechaSolicitud + '}';
    }

}
