package com.ecofield.modelos;

import com.ecofield.enums.EstadoTrabajoSolicitado;
import java.util.Date;

/**
 * Representa un trabajo solicitado en el sistema.
 *
 * @author Eduardo Olalde
 */
public class TrabajoSolicitado {

    private final int idSolicitud;
    private final int numParcela;
    private final int propietario;
    private final int idTipoTrabajo;
    private EstadoTrabajoSolicitado estado;
    private final Date fechaSolicitud;

    public TrabajoSolicitado(int idSolicitud, int numParcela, int propietario, int idTipoTrabajo, EstadoTrabajoSolicitado estado, Date fechaSolicitud) {
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

    public int getNumParcela() {
        return numParcela;
    }

    public int getPropietario() {
        return propietario;
    }

    public int getIdTipoTrabajo() {
        return idTipoTrabajo;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public EstadoTrabajoSolicitado getEstado() {
        return estado;
    }

    public void setEstado(EstadoTrabajoSolicitado estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "TrabajoSolicitado{" + "idSolicitud=" + idSolicitud + ", numParcela=" + numParcela + ", propietario=" + propietario + ", idTipoTrabajo=" + idTipoTrabajo + ", estado=" + estado + ", fechaSolicitud=" + fechaSolicitud + '}';
    }

}
