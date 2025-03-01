package com.ecofield.modelos;

/**
 * Representa un tipo de trabajo en el sistema.
 * Esta clase se utiliza para almacenar información sobre los diferentes tipos de trabajo disponibles en el sistema.
 * Cada tipo de trabajo tiene un identificador único y un nombre.
 * 
 * @author Eduardo Olalde
 */
public class TipoTrabajo {

    /** El identificador único del tipo de trabajo */
    private int idTipoTrabajo;

    /** El nombre del tipo de trabajo */
    private String nombre;

    /**
     * Constructor por defecto para crear un tipo de trabajo sin inicializar.
     */
    public TipoTrabajo() {
    }

    /**
     * Constructor para crear un tipo de trabajo con un identificador y un nombre.
     * 
     * @param idTipoTrabajo El identificador único del tipo de trabajo.
     * @param nombre El nombre del tipo de trabajo.
     */
    public TipoTrabajo(int idTipoTrabajo, String nombre) {
        this.idTipoTrabajo = idTipoTrabajo;
        this.nombre = nombre;
    }

    /**
     * Obtiene el identificador del tipo de trabajo.
     * 
     * @return El identificador único del tipo de trabajo.
     */
    public int getIdTipoTrabajo() {
        return idTipoTrabajo;
    }

    /**
     * Establece el identificador del tipo de trabajo.
     * 
     * @param idTipoTrabajo El identificador único del tipo de trabajo.
     */
    public void setIdTipoTrabajo(int idTipoTrabajo) {
        this.idTipoTrabajo = idTipoTrabajo;
    }

    /**
     * Obtiene el nombre del tipo de trabajo.
     * 
     * @return El nombre del tipo de trabajo.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del tipo de trabajo.
     * 
     * @param nombre El nombre del tipo de trabajo.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve una representación en formato de cadena del tipo de trabajo.
     * 
     * @return Una cadena que representa el tipo de trabajo con su identificador y nombre.
     */
    @Override
    public String toString() {
        return "TipoTrabajo{" + "idTipoTrabajo=" + idTipoTrabajo + ", nombre=" + nombre + '}';
    }

}
