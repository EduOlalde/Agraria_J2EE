package com.ecofield.modelos;

/**
 * Representa un rol en el sistema.
 * Esta clase se utiliza para almacenar información sobre los roles de los usuarios dentro del sistema.
 * Cada rol tiene un identificador único y un nombre.
 * 
 * @author Eduardo Olalde
 */
public class Rol {

    /** El identificador único del rol */
    private final int idRol;

    /** El nombre del rol */
    private final String nombre;

    /**
     * Constructor para crear un nuevo rol con un identificador y un nombre.
     * 
     * @param idRol El identificador único del rol.
     * @param nombre El nombre del rol.
     */
    public Rol(int idRol, String nombre) {
        this.idRol = idRol;
        this.nombre = nombre;
    }

    /**
     * Obtiene el identificador del rol.
     * 
     * @return El identificador único del rol.
     */
    public int getIdRol() {
        return idRol;
    }

    /**
     * Obtiene el nombre del rol.
     * 
     * @return El nombre del rol.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Genera un valor hash para el rol basado en su identificador.
     * Este método se utiliza para comparar objetos de tipo Rol en estructuras de datos como HashMap o HashSet.
     * 
     * @return El valor hash del rol.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + this.idRol;
        return hash;
    }

    /**
     * Compara este rol con otro objeto para determinar si son iguales.
     * Dos roles se consideran iguales si tienen el mismo identificador.
     * 
     * @param obj El objeto con el que se desea comparar este rol.
     * @return true si los objetos son iguales, false de lo contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Rol other = (Rol) obj;
        return this.idRol == other.idRol;
    }

    /**
     * Devuelve una representación en formato de cadena del rol.
     * 
     * @return Una cadena que representa el rol con su identificador y nombre.
     */
    @Override
    public String toString() {
        return "Rol{" + "idRol=" + idRol + ", nombre=" + nombre + '}';
    }

}
