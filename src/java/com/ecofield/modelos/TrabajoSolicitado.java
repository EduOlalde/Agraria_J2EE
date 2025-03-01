package com.ecofield.modelos;

import java.util.Date;

/**
 * Representa un trabajo solicitado en el sistema.
 * Esta clase se utiliza para almacenar información sobre una solicitud de trabajo
 * realizada en una parcela, incluyendo el número de parcela, el propietario, el tipo de trabajo solicitado,
 * el estado de la solicitud y la fecha en que se hizo la solicitud.
 * 
 * @author Eduardo Olalde
 */
public class TrabajoSolicitado {

    /** El identificador único de la solicitud de trabajo */
    private int idSolicitud;

    /** El número de parcela en la que se solicita el trabajo */
    private int numParcela;

    /** El identificador del propietario de la parcela */
    private int propietario;

    /** El identificador del tipo de trabajo solicitado */
    private int idTipoTrabajo;

    /** El estado de la solicitud (por ejemplo, "pendiente", "aceptada") */
    private String estado;

    /** La fecha en que se realizó la solicitud de trabajo */
    private Date fechaSolicitud;

    /**
     * Constructor para crear una solicitud de trabajo con los parámetros básicos (sin estado ni fecha).
     * 
     * @param numParcela El número de parcela en la que se solicita el trabajo.
     * @param propietario El identificador del propietario de la parcela.
     * @param idTipoTrabajo El identificador del tipo de trabajo solicitado.
     */
    public TrabajoSolicitado(int numParcela, int propietario, int idTipoTrabajo) {
        this.numParcela = numParcela;
        this.propietario = propietario;
        this.idTipoTrabajo = idTipoTrabajo;
    }

    /**
     * Constructor para crear una solicitud de trabajo con todos los parámetros.
     * 
     * @param idSolicitud El identificador único de la solicitud de trabajo.
     * @param numParcela El número de parcela en la que se solicita el trabajo.
     * @param propietario El identificador del propietario de la parcela.
     * @param idTipoTrabajo El identificador del tipo de trabajo solicitado.
     * @param estado El estado de la solicitud (por ejemplo, "pendiente", "aceptada").
     * @param fechaSolicitud La fecha en que se realizó la solicitud de trabajo.
     */
    public TrabajoSolicitado(int idSolicitud, int numParcela, int propietario, int idTipoTrabajo, String estado, Date fechaSolicitud) {
        this.idSolicitud = idSolicitud;
        this.numParcela = numParcela;
        this.propietario = propietario;
        this.idTipoTrabajo = idTipoTrabajo;
        this.estado = estado;
        this.fechaSolicitud = fechaSolicitud;
    }

    /**
     * Obtiene el identificador de la solicitud de trabajo.
     * 
     * @return El identificador único de la solicitud de trabajo.
     */
    public int getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * Establece el identificador de la solicitud de trabajo.
     * 
     * @param idSolicitud El identificador único de la solicitud de trabajo.
     */
    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /**
     * Obtiene el número de parcela en la que se solicita el trabajo.
     * 
     * @return El número de parcela.
     */
    public int getNumParcela() {
        return numParcela;
    }

    /**
     * Establece el número de parcela en la que se solicita el trabajo.
     * 
     * @param numParcela El número de parcela.
     */
    public void setNumParcela(int numParcela) {
        this.numParcela = numParcela;
    }

    /**
     * Obtiene el identificador del propietario de la parcela.
     * 
     * @return El identificador del propietario.
     */
    public int getPropietario() {
        return propietario;
    }

    /**
     * Establece el identificador del propietario de la parcela.
     * 
     * @param propietario El identificador del propietario.
     */
    public void setPropietario(int propietario) {
        this.propietario = propietario;
    }

    /**
     * Obtiene el identificador del tipo de trabajo solicitado.
     * 
     * @return El identificador del tipo de trabajo.
     */
    public int getIdTipoTrabajo() {
        return idTipoTrabajo;
    }

    /**
     * Establece el identificador del tipo de trabajo solicitado.
     * 
     * @param idTipoTrabajo El identificador del tipo de trabajo.
     */
    public void setIdTipoTrabajo(int idTipoTrabajo) {
        this.idTipoTrabajo = idTipoTrabajo;
    }

    /**
     * Obtiene el estado de la solicitud de trabajo.
     * 
     * @return El estado de la solicitud (por ejemplo, "pendiente", "aceptada").
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado de la solicitud de trabajo.
     * 
     * @param estado El estado de la solicitud (por ejemplo, "pendiente", "aceptada").
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene la fecha en que se realizó la solicitud de trabajo.
     * 
     * @return La fecha de la solicitud.
     */
    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    /**
     * Establece la fecha en que se realizó la solicitud de trabajo.
     * 
     * @param fechaSolicitud La fecha de la solicitud.
     */
    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    /**
     * Devuelve una representación en formato de cadena de la solicitud de trabajo.
     * 
     * @return Una cadena que representa la solicitud de trabajo con todos sus detalles.
     */
    @Override
    public String toString() {
        return "TrabajoSolicitado{" + "idSolicitud=" + idSolicitud + ", numParcela=" + numParcela + ", propietario=" + propietario + ", idTipoTrabajo=" + idTipoTrabajo + ", estado=" + estado + ", fechaSolicitud=" + fechaSolicitud + '}';
    }

}
