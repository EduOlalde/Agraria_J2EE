package com.ecofield.modelos;

import java.util.Date;

/**
 * Representa un trabajo en el sistema.
 * Esta clase se utiliza para almacenar información sobre un trabajo realizado en una parcela,
 * incluyendo detalles como la máquina utilizada, el maquinista, las fechas de inicio y fin,
 * el tiempo trabajado, el tipo de trabajo y su estado.
 * 
 * @author Eduardo Olalde
 */
public class Trabajo {

    /** El identificador único del trabajo */
    private int id;

    /** El número de parcela en la que se realiza el trabajo */
    private int numParcela;

    /** El identificador de la máquina utilizada en el trabajo */
    private int idMaquina;

    /** El identificador del maquinista que realiza el trabajo */
    private int idMaquinista;

    /** El identificador del propietario de la parcela */
    private int idPropietario;

    /** La fecha de inicio del trabajo */
    private Date fecInicio;

    /** La fecha de fin del trabajo */
    private Date fecFin;

    /** El número de horas dedicadas al trabajo */
    private int horas;

    /** El identificador del tipo de trabajo realizado */
    private int tipoTrabajo;

    /** El estado del trabajo (por ejemplo, "en progreso", "finalizado") */
    private String estado;

    /**
     * Constructor por defecto para crear un trabajo sin inicializar.
     */
    public Trabajo() {
    }

    /**
     * Constructor para crear un trabajo con los parámetros especificados.
     * 
     * @param id El identificador único del trabajo.
     * @param numParcela El número de parcela en la que se realiza el trabajo.
     * @param idMaquina El identificador de la máquina utilizada en el trabajo.
     * @param idMaquinista El identificador del maquinista que realiza el trabajo.
     * @param idPropietario El identificador del propietario de la parcela.
     * @param fecInicio La fecha de inicio del trabajo.
     * @param fecFin La fecha de fin del trabajo.
     * @param horas El número de horas dedicadas al trabajo.
     * @param tipoTrabajo El identificador del tipo de trabajo realizado.
     * @param estado El estado del trabajo.
     */
    public Trabajo(int id, int numParcela, int idMaquina, int idMaquinista, int idPropietario, Date fecInicio, Date fecFin, int horas, int tipoTrabajo, String estado) {
        this.id = id;
        this.numParcela = numParcela;
        this.idMaquina = idMaquina;
        this.idMaquinista = idMaquinista;
        this.idPropietario = idPropietario;
        this.fecInicio = fecInicio;
        this.fecFin = fecFin;
        this.horas = horas;
        this.tipoTrabajo = tipoTrabajo;
        this.estado = estado;
    }

    /**
     * Obtiene el identificador del trabajo.
     * 
     * @return El identificador único del trabajo.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del trabajo.
     * 
     * @param id El identificador único del trabajo.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el número de parcela en la que se realiza el trabajo.
     * 
     * @return El número de parcela.
     */
    public int getNumParcela() {
        return numParcela;
    }

    /**
     * Establece el número de parcela en la que se realiza el trabajo.
     * 
     * @param numParcela El número de parcela.
     */
    public void setNumParcela(int numParcela) {
        this.numParcela = numParcela;
    }

    /**
     * Obtiene el identificador de la máquina utilizada en el trabajo.
     * 
     * @return El identificador de la máquina.
     */
    public int getIdMaquina() {
        return idMaquina;
    }

    /**
     * Establece el identificador de la máquina utilizada en el trabajo.
     * 
     * @param idMaquina El identificador de la máquina.
     */
    public void setIdMaquina(int idMaquina) {
        this.idMaquina = idMaquina;
    }

    /**
     * Obtiene el identificador del maquinista que realiza el trabajo.
     * 
     * @return El identificador del maquinista.
     */
    public int getIdMaquinista() {
        return idMaquinista;
    }

    /**
     * Establece el identificador del maquinista que realiza el trabajo.
     * 
     * @param idMaquinista El identificador del maquinista.
     */
    public void setIdMaquinista(int idMaquinista) {
        this.idMaquinista = idMaquinista;
    }

    /**
     * Obtiene el identificador del propietario de la parcela.
     * 
     * @return El identificador del propietario.
     */
    public int getIdPropietario() {
        return idPropietario;
    }

    /**
     * Establece el identificador del propietario de la parcela.
     * 
     * @param idPropietario El identificador del propietario.
     */
    public void setIdPropietario(int idPropietario) {
        this.idPropietario = idPropietario;
    }

    /**
     * Obtiene la fecha de inicio del trabajo.
     * 
     * @return La fecha de inicio del trabajo.
     */
    public Date getFecInicio() {
        return fecInicio;
    }

    /**
     * Establece la fecha de inicio del trabajo.
     * 
     * @param fecInicio La fecha de inicio del trabajo.
     */
    public void setFecInicio(Date fecInicio) {
        this.fecInicio = fecInicio;
    }

    /**
     * Obtiene la fecha de fin del trabajo.
     * 
     * @return La fecha de fin del trabajo.
     */
    public Date getFecFin() {
        return fecFin;
    }

    /**
     * Establece la fecha de fin del trabajo.
     * 
     * @param fecFin La fecha de fin del trabajo.
     */
    public void setFecFin(Date fecFin) {
        this.fecFin = fecFin;
    }

    /**
     * Obtiene el número de horas dedicadas al trabajo.
     * 
     * @return El número de horas.
     */
    public int getHoras() {
        return horas;
    }

    /**
     * Establece el número de horas dedicadas al trabajo.
     * 
     * @param horas El número de horas.
     */
    public void setHoras(int horas) {
        this.horas = horas;
    }

    /**
     * Obtiene el identificador del tipo de trabajo realizado.
     * 
     * @return El identificador del tipo de trabajo.
     */
    public int getTipoTrabajo() {
        return tipoTrabajo;
    }

    /**
     * Establece el identificador del tipo de trabajo realizado.
     * 
     * @param tipoTrabajo El identificador del tipo de trabajo.
     */
    public void setTipoTrabajo(int tipoTrabajo) {
        this.tipoTrabajo = tipoTrabajo;
    }

    /**
     * Obtiene el estado del trabajo (por ejemplo, "en progreso", "finalizado").
     * 
     * @return El estado del trabajo.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado del trabajo.
     * 
     * @param estado El estado del trabajo.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Devuelve una representación en formato de cadena del trabajo.
     * 
     * @return Una cadena que representa el trabajo con todos sus detalles.
     */
    @Override
    public String toString() {
        return "Trabajo{" + "id=" + id + ", numParcela=" + numParcela + ", idMaquina=" + idMaquina + ", idMaquinista=" + idMaquinista + ", idPropietario=" + idPropietario + ", fecInicio=" + fecInicio + ", fecFin=" + fecFin + ", horas=" + horas + ", tipoTrabajo=" + tipoTrabajo + ", estado=" + estado + '}';
    }

}
